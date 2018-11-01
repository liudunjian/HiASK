package com.hisense.hibeans.bot.response;

/**
 * Created by liudunjian on 2018/5/7.
 */

public class MaterialImage {

    private String materialId;
    private String tenantId;
    private String materialPic;

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMaterialPic() {
        return materialPic;
    }

    public void setMaterialPic(String materialPic) {
        this.materialPic = materialPic;
    }
}
