package com.sample;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

	public static void main(String[] args) throws Exception {
		CamelContext context1 = new DefaultCamelContext();
		context1.addRoutes(new FileRouteBuilder());
		context1.start();
		Thread.sleep(100000);
	}

	static class FileRouteBuilder extends RouteBuilder {
		public void configure() {
			from("file://target/input?delay=1000&readLock=rename").process(new Processor() {
				public void process(Exchange msg) throws InterruptedException {
					File file = msg.getIn().getBody(File.class);
					System.out.println(file.getName());
					Thread.sleep(1000);
				}
			});
		}
	}

}
