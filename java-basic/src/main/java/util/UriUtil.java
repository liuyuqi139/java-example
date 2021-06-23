package util;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * uri相关工具
 */
public class UriUtil {
    @Test
    public void generateUrlTest() {
        System.out.println(UriUtil.generateUrl("http://lol-mall-test.aixuexi.com/h5-app-live/index.html",
                "code",
                "123456"));

        System.out.println(UriUtil.generateUrl("http://lol-mall-test.aixuexi.com/h5-app-live/index.html?code=123456",
                "id",
                "123"));
    }

    private static String generateUrl(String urlPrefix, String urlParameter, String urlValue) {
        return UriComponentsBuilder.fromUriString(urlPrefix).queryParam(urlParameter, urlValue).build().toString();
    }
}
