package com.just.fast.utils.net;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * 持久化 Cookie
 *
 * @webUrl [http://blog.csdn.net/CPPAlien/article/details/50328477]
 * @webUrl [https://gist.github.com/franmontiel/ed12a2295566b7076161]
 */
public class PersistentCookieStore implements CookieStore {

  @Override
  public void add(URI uri, HttpCookie cookie) {

  }

  @Override
  public List<HttpCookie> get(URI uri) {
    return null;
  }

  @Override
  public List<HttpCookie> getCookies() {
    return null;
  }

  @Override
  public List<URI> getURIs() {
    return null;
  }

  @Override
  public boolean remove(URI uri, HttpCookie cookie) {
    return false;
  }

  @Override
  public boolean removeAll() {
    return false;
  }

}
