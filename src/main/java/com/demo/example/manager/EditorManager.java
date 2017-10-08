package com.demo.example.manager;

import com.demo.example.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * EditorManager
 *
 * @author xuxiang
 * @since 17/10/8
 */
@Component
public class EditorManager {

    public String encryption(Long pageId) {
        String idstr = String.valueOf(pageId << 2);
        String pageCode = UrlUtils.encode(idstr.getBytes());

        return pageCode;
    }

    public Long decrypt(String pageCode) {
        if (StringUtils.isBlank(pageCode)) {
            return 0L;
        }

        byte[] bytes = UrlUtils.decode(pageCode);

        return Long.parseLong(new String(bytes)) >> 2;
    }
}
