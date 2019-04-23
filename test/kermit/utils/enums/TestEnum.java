package kermit.utils.enums;

/**
 * @Date: 2019/4/2313:13
 * @Author: BoyuSun
 * @Description:
 */
public enum TestEnum implements KEnum{
    T(1, "ddd");

    private Integer id;
    private String desc;
    TestEnum(Integer id, String desc){
        this.id = id;
        this.desc = desc;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
