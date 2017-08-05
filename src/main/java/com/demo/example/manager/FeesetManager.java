package com.demo.example.manager;

import com.demo.example.data.po.FeeSet;
import com.demo.example.data.po.UserFeeSet;
import com.demo.example.data.repository.Repository;
import org.nutz.dao.Cnd;
import org.nutz.dao.DaoException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class FeesetManager implements InitializingBean {
    @Autowired
    private Repository repository;

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            FeeSet feeSet = new FeeSet();
            feeSet.setCode(0);
            feeSet.setTitle("免费套餐");
            feeSet.setDescription("免费体验套餐");
            feeSet.setMaxPages(3L);
            feeSet.setMaxPageView(10000L);
            feeSet.setValidity(1);
            feeSet.setPrice(0.0);
            repository.insert(feeSet);

            feeSet = new FeeSet();
            feeSet.setCode(1);
            feeSet.setTitle("基础体验套餐");
            feeSet.setDescription("适用于起步阶段的公众号");
            feeSet.setMaxPages(10L);
            feeSet.setMaxPageView(100000L);
            feeSet.setValidity(1);
            feeSet.setPrice(99.0);
            repository.insert(feeSet);

            feeSet = new FeeSet();
            feeSet.setCode(2);
            feeSet.setTitle("高级运营套餐");
            feeSet.setDescription("适用于粉丝量过万的成熟公众号");
            feeSet.setMaxPages(20L);
            feeSet.setMaxPageView(1000000L);
            feeSet.setValidity(1);
            feeSet.setPrice(499.0);
            repository.insert(feeSet);

            feeSet = new FeeSet();
            feeSet.setCode(3);
            feeSet.setTitle("尊享会员套餐");
            feeSet.setDescription("适用于文章阅读量过万的热门公众号");
            feeSet.setMaxPages(0L);
            feeSet.setMaxPageView(10000000L);
            feeSet.setValidity(1);
            feeSet.setPrice(2999.0);
            repository.insert(feeSet);
        } catch (DaoException e) {
            //
            e.printStackTrace();
        }
    }

    public void initFreeFeeSet(Long userId) {

        UserFeeSet free = repository.fetch(UserFeeSet.class, Cnd.where("user_id", "=", userId)
                .and("fee_set_code", "=", 0));
        if (null == free) {
            final FeeSet feeSet = repository.fetch(FeeSet.class, Cnd.where("code", "=", 0));
            free = new UserFeeSet();
            free.setUserId(userId);
            free.setFeeSetCode(0);
            free.setCurrPageView(0L);
            free.setMaxPageView(feeSet.getMaxPageView());
            free.setGmtCreate(new Date());
            free.setGmtModified(new Date());
            free.setGmtStart(new Date());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 1);
            free.setGmtEnd(calendar.getTime());
            free.setStatus(1);
            repository.insert(free);
        }
    }

    public FeeSet getUserCurrFeeset(long userId) {

        final List<UserFeeSet> feeSets = getUserAllFeeset(userId);

        UserFeeSet userFeeSet = feeSets.stream()
                .filter(e -> e.getCurrPageView() < e.getMaxPageView())
                .findFirst()
                .orElse(null);

        if (null == userFeeSet && !feeSets.isEmpty()) {
            userFeeSet = feeSets.get(feeSets.size() - 1);
        }

        return repository.fetch(FeeSet.class, Cnd.where("code", "=", userFeeSet.getFeeSetCode()));
    }

    public List<UserFeeSet> getUserAllFeeset(long userId) {

        final List<UserFeeSet> feeSets = repository.query(UserFeeSet.class, Cnd.where("user_id", "=", userId)
                .and("status", "=", 1));
        feeSets.sort((a, b) -> a.getFeeSetCode() > b.getFeeSetCode() ? 1 : -1);

        return feeSets;
    }
}

