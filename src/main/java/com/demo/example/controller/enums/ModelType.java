package com.demo.example.controller.enums;

/**
 * ModelType
 *
 * @author xuxiang
 * @since 17/7/15
 */
public enum ModelType implements GenericEnum {

    TEXT(0, "文字"),

    IMAGE(1, "图片"),

    CAROUSEL(2, "轮播图推荐位"),

    SLIDE(3, "可滑动大图推荐位"),

    SLIDE_SQUARE(4, "可滑动正方形推荐位"),

    SLIDE_RECTANGLE(5, "可滑动长方形推荐位"),

    LIST(6, "列表大图推荐位"),

    LIST_MIX(7, "列表图文推荐位"),

    SQUARE(8, "正方形推荐位"),

    MIX(9, "聚合推荐位"),

    TOP_BRAND_BAR(10, "顶部品牌栏"),

    ;


    private int code;
    private String desc;


    ModelType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }

    public static ModelType codeOf(int value) {
        final ModelType[] sources = ModelType.values();

        for (ModelType src : sources) {
            if (src.code == value) {
                return src;
            }
        }

        return null;
    }
}
