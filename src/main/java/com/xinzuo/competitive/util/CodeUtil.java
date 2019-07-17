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
    public  String getCode(String projectsId){
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
        codes.forEach(System.out::println);
        if (codes.size()<1){
            return "001";
        }

        for (int c=0, ic=1;c<codes.size();c++,ic++){
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

        }
        return s;
    }

    public  Integer getCode0(Integer classifyId){
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
        codes.forEach(System.out::println);
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
  /*  public static void main(String[] args) {

       System.out.println(stringFilter("(大 家...")+"0000000");

    }*/

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
