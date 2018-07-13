package com.dante.util;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.dante.ws.demo.User;

public class XmlUtil {

	public static void main(String[] args) throws MalformedURLException {
		// convertObjectToXml(new User(1, "testName", "testRole"));

		URL url = new URL("http://localhost:8080/TheDawn/ws/test/xml");
		convertXmlToObjectByURL(url);
	}

	public static User convertXmlToObjectByURL(URL url) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();

			User user = (User) unMarshaller.unmarshal(url);
			System.out.println(user);
			return user;
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String convertObjectToXml(User user) {

		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(user, stringWriter);

			System.out.println(stringWriter.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return stringWriter.toString();
	}

}
