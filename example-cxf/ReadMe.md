Esempio per esporre un servizio CXF semplice:
===

Il profilo da utilizzare è:

1. example-cxf

Espone un servizio CXF complesso.

Il server utilizza il servizio fabric normalmente disponibile tramite il componente camel **fabric:** e
registra due diversi server cxf (jaxws implementano tutti e due la stessa classe ma due istanze diverse, una risponde Hi e l'altra Hello)
rendendoli disponibili tramite il path fabric cxf/demo

``` html
    <reference id="curator" interface="org.apache.curator.framework.CuratorFramework" />

    <!-- The FabricFailOverFeature will try to access other service endpoint with round rad -->
    <bean id="fabicLoadBalancerFeature" class="io.fabric8.cxf.FabricLoadBalancerFeature">
        <property name="curator" ref="curator" />
        <property name="fabricPath" value="cxf/demo" />
    </bean>

    <!-- setup the feature on the bus to help publish the services to the fabric-->
    <cxf:bus bus="cxf">
        <cxf:features>
            <ref component-id="fabicLoadBalancerFeature"/>
        </cxf:features>
    </cxf:bus>

    <bean id="hello1" class="io.fabric8.demo.cxf.server.HelloImpl">
        <property name="hello" value="Hi"/>
    </bean>

    <bean id="hello2" class="io.fabric8.demo.cxf.server.HelloImpl">
        <property name="hello" value="Hello"/>
    </bean>


    <!-- publish the service with the address of fail, cxf client will get the simulated IOException -->
    <jaxws:server id="service1" serviceClass="io.fabric8.demo.cxf.Hello" address="http://localhost:9000/server/server1">
        <jaxws:serviceBean>
             <ref component-id="hello1" />
        </jaxws:serviceBean>
    </jaxws:server>

    <jaxws:server id="service2" serviceClass="io.fabric8.demo.cxf.Hello" address="http://localhost:9000/server/server2">
        <jaxws:serviceBean>
            <ref component-id="hello2" />
        </jaxws:serviceBean>
    </jaxws:server>
```

Nei sorgenti, precisamente nel modulo cxf-client, c'è l'esempio di un main che si connette a Zookeeper, recupera la url
del servizio cxf/demo ed esegue una chiamata al webservice esposto.