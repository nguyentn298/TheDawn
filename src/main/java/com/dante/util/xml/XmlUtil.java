package com.dante.util.xml;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class XmlUtil {

	@Test
	public void convertXmlToObjectByURL() throws MalformedURLException {

		try {

			URL url = new URL("http://localhost:8080/TheDawn/ws/test/xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();

			User user = (User) unMarshaller.unmarshal(url);					// unmarshal from url
//			List<User> users = (List<User>) unMarshaller.unmarshal(file); 	// unmarshal from file
			System.out.println(user);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void convertObjectToXml() {

		User user = getUser();
		StringWriter stringWriter = new StringWriter();
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(user, stringWriter);		// marshal to String
//			marshaller.marshal(user, file); 			// marshal and copy to File
//			marshaller.marshal(user, System.out);

			System.out.println(stringWriter.toString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
	
	public User getUser() {
		return new User(3, "Talon", "Assassin");
	}

}
