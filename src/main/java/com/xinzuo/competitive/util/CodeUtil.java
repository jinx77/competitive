package com.xinzuo.competitive.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.CompanyDao;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.pojo.Company;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.CompanyClassifyService;
import com.xinzuo.competitive.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CodeUtil {
    @Autowired
    QualificationDao qualificationDao;
    @Autowired
    CompanyDao companyDao;
    public synchronized  String getCode(String projectsId){
        String s=null;
        //有竞标资格的时候给一个竞选编号
        QueryWrapper<Qualification> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("projects_id",projectsId);
        List<Qualification> qualificationList=qualificationDao.selectList(queryWrapper);
        List<Integer> codes=new ArrayList<>();
        qualificationList.forEach(q -> {
            if (q.getQualificationNumber()!=null){
                codes.add(Integer.valueOf(q.getQualificationNumber()));
            }
        });
        Collections.sort(codes);
       // codes.forEach(System.out::println);
        if (codes.size()<1){
            return "001";
        }
       for (int i=1;i<codes.size()+2;i++){
           int c=0;
          for (int j:codes){
               if (i==j){
                  c=c+1;
               }
           }
            if (c==0){
                s=String.valueOf(i);
                if (s.length()==1){
                    s="00"+s;
                    return s;
                }else if (s.length()==2){
                    s="0"+s;
                    return s;
                }
            }
            if (c>1){
              int x=  Collections.max(codes)+1;
               String ss=String.valueOf(x);

                String ii=String.valueOf(i);
                if (ii.length()==1){
                    ii="00"+ii;
                }else if (ii.length()==2){
                    ii="0"+ii;
                }
                QueryWrapper<Qualification> qualificationQueryWrapper=new QueryWrapper<>();
                qualificationQueryWrapper.eq("projects_id",projectsId);
                qualificationQueryWrapper.eq("qualification_number",ii);
                List<Qualification> qualifications=qualificationDao.selectList(qualificationQueryWrapper);
               for (Qualification qualification:qualifications){
                   if (ss.length()==1){
                       ss="00"+ss;
                   }else if (ss.length()==2){
                       ss="0"+ss;
                   }
                    Qualification q=new Qualification();
                    q.setQualificationId(qualification.getQualificationId());
                    q.setQualificationNumber(ss);
                    qualificationDao.updateById(q);
                   codes.add(x);
                   x=x+1;
                   ss=String.valueOf(x);
                }
            }
       }
        s=String.valueOf(Collections.max(codes)+1);
        if (s.length()==1){
            s="00"+s;
        }else if (s.length()==2){
            s="0"+s;
        }

       /* for (int c=0, ic=1;c<codes.size();c++,ic++){
            if (!codes.get(c).equals(ic)){
                s=String.valueOf(ic);
                if (s.length()==1){
                    s="00"+s;
                    break;
                }else if (s.length()==2){
                    s="0"+s;
                    break;
                }

            }else {
                 s=String.valueOf(codes.size()+1);
                if (s.length()==1){
                    s="00"+s;
                }else if (s.length()==2){
                    s="0"+s;
                }
            }

        }*/
        return s;
    }

    public synchronized Integer getCode0(Integer classifyId){
        QueryWrapper<Company> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("company_classify_id",classifyId);
        List<Company> companyList=companyDao.selectList(queryWrapper);
        List<Integer> codes=new ArrayList<>();
        companyList.forEach(c -> {
            if (c.getCompanyNumber()!=null){
                codes.add(c.getCompanyNumber());
            }
        });
        Collections.sort(codes);
       // codes.forEach(System.out::println);
        if (codes.size()<1){
            return 1;
        }
        for (int c=0, ic=1;c<codes.size();c++,ic++){
            if (!codes.get(c).equals(ic)){
                  return ic;
            }
        }
        return codes.size()+1;
    }

    /**
     * 过滤特殊字符
     * @param str
     * @return
     *
     * \u00A0 特殊的空格
     */
    public  String stringFilter (String str){
        //String regEx="[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        String regEx="[\\u00A0\\s\"`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
