package com.xinzuo.competitive.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinzuo.competitive.dao.CompanyClassifyDao;
import com.xinzuo.competitive.dao.QualificationDao;
import com.xinzuo.competitive.pojo.CompanyClassify;
import com.xinzuo.competitive.pojo.Qualification;
import com.xinzuo.competitive.service.CompanyClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class CodeUtil {
    @Autowired
    QualificationDao qualificationDao;
    @Autowired
    CompanyClassifyService companyClassifyService;
    public  String getCode(String projectsId){

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
        if (codes.size()<1){
            return "001";
        }

        for (int c=0, ic=1;c<codes.size();c++,ic++){
            if (codes.get(c)!=ic){
                QueryWrapper<Qualification> qw=new QueryWrapper<>();
                queryWrapper.eq("qualification_number",ic).eq("projects_id",projectsId);
                List<Qualification> qualifications= qualificationDao.selectList(qw);
                if (qualifications.size()==0){
                    String s=String.valueOf(ic);
                    if (s.length()==1){
                        s="00"+s;
                    }else if (s.length()==2){
                        s="0"+s;
                    }
                   return s;
                }
            }else {
                String s=String.valueOf(codes.size()+1);
                if (s.length()==1){
                    s="00"+s;
                }else if (s.length()==2){
                    s="0"+s;
                }
                return s;
            }

        }

        return null;

    }

    public  int getSort() {

        List<CompanyClassify> companyClassifyList = companyClassifyService.list();
        int i= companyClassifyList.size();
        String s="woshinima";


        return i;
    }

    public static void main(String[] args) {

       System.out.println(isChineseChar("(大家...")+"0000000");

    }


    public static String isChineseChar(String s) {
        //return String.valueOf(c).matches("[\u4e00-\u9fa5]");

        StringBuffer sb=new StringBuffer(s);
        for (int i=0;i<sb.length();i++){
          //String jc=  String.valueOf(sb.append(i));
            System.out.println("---------"+s.indexOf(i));
            /*if (*//*jc.matches("[\u4e00-\u9fa5]")==true||*//*jc.equals("(")||jc.equals(")")){
                System.out.println("---------");
                sb.append(jc);
            }*/
        }
        s=sb.toString();
        return s;
    }
}
