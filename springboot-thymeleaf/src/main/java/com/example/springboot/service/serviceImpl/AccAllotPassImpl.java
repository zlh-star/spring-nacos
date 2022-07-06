package com.example.springboot.service.serviceImpl;

import com.example.springboot.dao.AccAllotPassDao;
import com.example.springboot.dao.AccAllotPassBo;
import com.example.springboot.service.IAccAllotPassService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("AccAllotPassDao")
public class AccAllotPassImpl implements AccAllotPassDao, IAccAllotPassService {
    @Autowired
    public AccAllotPassDao accAllotPassDao;
    @Override
    public void AccAllotPass(List<AccAllotPassBo> accAllotPass) {
//        AccAllotPassBo accAllotPassBo=new AccAllotPassBo();
        List<AccAllotPassBo> allotPassBos =new ArrayList<>();

         accAllotPassDao.AccAllotPass(allotPassBos);
//                 .onErrorResume(e->
//                         accAllotPassDao.getAll(accAllotPassBo.getId())
//                 .flatMap(originalAccAllotPass-> {
//                     accAllotPassBo.setId(originalAccAllotPass.);
//                     accAllotPassDao.AccAllotPass(allotPassBos);
//                 }));
    }

    @Override
    public void delAccountByid(String id, List<String> account) {

        accAllotPassDao.delAccountByid(id, account);
    }

    @Override
    public List<String> getAll(String id) {
        return accAllotPassDao.getAll(id);
    }

    @Override
    public List<AccAllotPassBo> updateAccountUserById(String account, String password, String id, String shortname, String fullname) {
       List<AccAllotPassBo> allotPassBos=new ArrayList<>();
        AccAllotPassBo bo=new AccAllotPassBo();
        bo.getAccount();
        bo.getAccount();
        bo.getFullname();
        bo.getPassword();
        bo.getShortname();
        allotPassBos.add(bo);
        return allotPassBos;
    }

    @Override
    public void relAllotShortnameById(String id, List<String> shortname) {
        List<String> Shortname=new ArrayList<>();
        List<AccAllotPassBo> allotPassBos=new ArrayList<>();
        if(!StringUtils.isEmpty(id)){
            Shortname=accAllotPassDao.getAll(id);
        }
        if(shortname!=null&& shortname.size()>0&& Shortname!=null&& Shortname.size()>=0){
           Shortname.removeAll(shortname);
        }else{
            for(String short1:Shortname)
            {
                allotPassBos.add(new AccAllotPassBo("3","213",id,short1,"zhaolinhai"));
            }
        }
        accAllotPassDao.AccAllotPass(allotPassBos);
    }
}
