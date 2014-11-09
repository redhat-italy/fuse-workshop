package it.redhat.quickstarts.fuse.provider;

import it.redhat.quickstarts.fuse.api.Greeter;

public class GreeterImpl implements Greeter {

  @Override
  public String greet(String name) {
    return "Hello " + name;
  }
}
