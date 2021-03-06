package com.example.demo.http;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.json.JsonUtil;
import com.example.demo.security.AESEncoder;
import com.example.demo.security.CryptoType;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 * <p>
 * Date: 17-3-22
 * Time: 上午10:59
 *
 * @author heroin.nee@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class AbstractHttpClient extends HttpClient {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractHttpClient.class);

    protected AbstractHttpClient(final HttpMethod method, final String url) {
        super(method, url);
    }

    protected AbstractHttpClient(final HttpMethod method, final String url, final String charset) {
        super(method, url, charset);
    }

    protected AbstractHttpClient(final HttpMethod method, final String url, final Charset charset) {
        super(method, url, charset);
    }

    public AbstractHttpClient json(final String body) {
        StringEntity entity = new StringEntity(body, this.requestBuilder.getCharset());
        entity.setContentType(ContentType.create(ContentType.APPLICATION_JSON.getMimeType(), this.charset).toString());
        this.requestBuilder.setEntity(entity);
        return this;
    }

    public AbstractHttpClient json(final String body, final String key, final CryptoType type)
            throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.setHeader(HEADER_CONTENT_CRYPTO_TYPE, type.toString());
        return json(AESEncoder.encrypt(body, key));
    }

    public AbstractHttpClient json(final String body, final String key)
            throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return json(body, key, CryptoType.AES);
    }

    public <T> AbstractHttpClient json(final T body) {
        return json(JsonUtil.toJson(body));
    }

    public <T> AbstractHttpClient json(final T body, final String key)
            throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return json(JsonUtil.toJson(body), key);
    }

    public <T> AbstractHttpClient form(final T body) {
        try{
            Map<String, String> props = BeanUtils.describe(body);
            props.remove("class");
            for (Map.Entry<String, String> entry : props.entrySet()) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    this.addParameter(entry.getKey(), entry.getValue());
                }
            }
        }catch (Exception e) {
            LOG.error("IllegalAccessException", e);
        }

        return this;
    }

    public AbstractHttpClient addParameter(final String name, final String value) {
        super.addParameter(name, value);
        return this;
    }
}
