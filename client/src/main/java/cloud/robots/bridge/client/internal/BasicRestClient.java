package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.exceptions.BridgeHttpException;
import cloud.robots.bridge.client.exceptions.BridgeJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

abstract class BasicRestClient {

  private static final String APPLICATION_JSON = "application/json";
  private static final String CANT_SERIALIZE_INTO_JSON = "can't serialize into JSON";
  private static final String EXCEPTION_EXECUTING_REQUEST = "exception executing request";
  private static final String ERROR_HTTP_STATUS = "error getting http status";
  private static final String ERROR_GETTING_CONTENT = "error getting content";
  private static final String ERROR_PARSING_JSON = "error parsing json";
  private static final String RESPONSE_CODE_IS_NOT = "response code is not";
  private static final String WAS = "was";

  private final String baseUrl;
  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final int timeout;

  BasicRestClient(String baseUrl, int timeout) {
    this.baseUrl = baseUrl;
    this.timeout = timeout;
  }

  <RequestType, ResponseType> ResponseType request(Function<String, Request> method, String url, RequestType content,
                                                   Class<ResponseType> type, int status) throws BridgeException {
    final String json = (content != null) ? getJsonRequest(content) : null;
    final Response response = execute(method.apply(baseUrl + url), json);
    final HttpResponse httpResponse = getHttpResponse(status, response);

    try (final InputStream jsonResult = getJsonResult(httpResponse)) {
      return getValue(jsonResult, type);
    } catch (IOException e) {
      throw new BridgeHttpException(ERROR_GETTING_CONTENT, e);
    }
  }

  <ResponseType> ResponseType request(Function<String, Request> method, String url, Class<ResponseType> type,
                                      int status) throws BridgeException {
    return request(method, url, null, type, status);
  }

  private Response execute(Request request, String json) throws BridgeHttpException {
    final Response response;
    try {
      Request newRequest = request
          .addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON)
          .addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
          .connectTimeout(timeout)
          .socketTimeout(timeout);
      if (json != null) {
        newRequest = newRequest.bodyString(json, ContentType.APPLICATION_JSON);
      }
      response = newRequest.execute();
    } catch (IOException e) {
      throw new BridgeHttpException(EXCEPTION_EXECUTING_REQUEST, e);
    }
    return response;
  }

  private <Type> Type getValue(InputStream jsonResult, Class<Type> type) throws BridgeJsonException {
    final Type value;
    try {
      value = objectMapper.readValue(jsonResult, type);
    } catch (IOException e) {
      throw new BridgeJsonException(ERROR_PARSING_JSON, e);
    }
    return value;
  }

  private <Type> String getJsonRequest(Type content) throws BridgeJsonException {
    final String json;
    try {
      json = objectMapper.writeValueAsString(content);
    } catch (JsonProcessingException e) {
      throw new BridgeJsonException(CANT_SERIALIZE_INTO_JSON, e);
    }
    return json;
  }

  private InputStream getJsonResult(HttpResponse httpResponse) throws BridgeHttpException {
    final InputStream jsonResult;
    try {
      jsonResult = httpResponse.getEntity().getContent();
    } catch (IOException e) {
      throw new BridgeHttpException(ERROR_GETTING_CONTENT, e);
    }
    return jsonResult;
  }

  private HttpResponse getHttpResponse(int status, Response response) throws BridgeHttpException {
    final HttpResponse httpResponse;
    try {
      httpResponse = response.returnResponse();
      int actualStatus = httpResponse.getStatusLine().getStatusCode();
      if (actualStatus != status) {
        throw new BridgeHttpException(RESPONSE_CODE_IS_NOT + " " + status + " " + WAS + " " + actualStatus);
      }
    } catch (IOException e) {
      throw new BridgeHttpException(ERROR_HTTP_STATUS, e);
    }
    return httpResponse;
  }
}
