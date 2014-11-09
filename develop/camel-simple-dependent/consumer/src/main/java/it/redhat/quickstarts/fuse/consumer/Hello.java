package it.redhat.quickstarts.fuse.consumer;

import it.redhat.quickstarts.fuse.api.Greeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hello {

  private final static Logger log = LoggerFactory.getLogger(Hello.class);
  private Greeter greeter;
  private Boolean active = true;

  public Hello() {
  }

  public void init() {
    new Thread(new Runnable() {

      @Override
      public void run() {
        while (active) {
          try {
            Thread.sleep(1000);
            log.info("Method call Hello: " + greeter.greet("World"));
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
          }
        }
      }

    }).start();
  }

  public void setGreeter(Greeter greeter) {
    this.greeter = greeter;
  }

  public void stop() {
    active = false;
  }

}
