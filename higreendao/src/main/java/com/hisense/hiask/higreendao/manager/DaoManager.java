package com.hisense.hiask.higreendao.manager;

import com.hisense.hibeans.bot.model.BotMessage;
import com.hisense.hibeans.database.dao.BotMessageDao;
import com.hisense.hibeans.database.dao.DaoMaster;
import com.hisense.hibeans.database.dao.DaoSession;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.HiUtils;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by liudunjian on 2018/6/22.
 */

public class DaoManager {

    private DaoSession daoSession;

    public static DaoManager getInstance() {
        return DaoHolder.INSTANCE;
    }

    public void initDaoDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(HiUtils.getContext(), "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    /*********************insert methods*************************/

    public void insertBotMessage(BotMessage botMessage) {
        if (EmptyUtils.isNotEmpty(botMessage))
            daoSession.getBotMessageDao().insertOrReplace(botMessage);
    }

    public void insertBotMessageList(List<BotMessage> botMessageList) {
        if (EmptyUtils.isNotEmpty(botMessageList))
            daoSession.getBotMessageDao().insertOrReplaceInTx(botMessageList);
    }

    /********************get methods*****************************/

    public List<BotMessage> loadAllBotMessages() {
        return daoSession.getBotMessageDao().loadAll();
    }

    public List<BotMessage> loadAllBotMessagesByTime(int limit) {
        QueryBuilder queryBuilder = daoSession.getBotMessageDao().queryBuilder();
        queryBuilder.orderDesc(BotMessageDao.Properties.SendTime);
        if (limit > 0) {
            queryBuilder.limit(limit);
        }

        return queryBuilder.list();
    }

    private static final class DaoHolder {
        private static final DaoManager INSTANCE = new DaoManager();
    }
}
