package edu.jiangxin.zhihu.core;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {
	
	Config config = null;
	
	@Before
	public void setUp() throws JAXBException {
		File file = new File("target/test-classes/ConfigTest.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        config = (Config) jaxbUnmarshaller.unmarshal(file);
	}
	
	
	@Test
	public void testParser01() {
		assertEquals("edge", config.getBrowser().getType());
		assertEquals("20.10240", config.getBrowser().getVersion());
		assertEquals("tmp\\Cookie\\cookie.txt", config.getCookie().getPath());
		assertEquals("dfjlsdajfsfvn", config.getUser().getUsername());
		assertEquals("vkasdfjlsd", config.getUser().getPassword());
		assertEquals(6,config.getTargets().size());
		assertEquals("unfollow", config.getTargets().get(0).getMethod());
		assertEquals(Integer.MAX_VALUE, config.getTargets().get(0).getOperated_num());
		assertEquals(false, config.getTargets().get(0).isShutdown());
	}
	
	@Test
	public void testPaser02() {
		assertEquals(Kind.SOMETOPIC_FOLLOWERS, config.getTargets().get(0).getKind());
		assertEquals(Kind.SOMEQUESTION_FOLLOWERS, config.getTargets().get(1).getKind());
		assertEquals(Kind.SOMEPEOPLE_FOLLOWEES, config.getTargets().get(2).getKind());
		assertEquals(Kind.SOMEPEOPLE_FOLLOWERS, config.getTargets().get(3).getKind());
		assertEquals(Kind.SOMEPEOPLE_COLUMNS, config.getTargets().get(4).getKind());
		assertEquals(Kind.SOMEPEOPLE_TOPICS, config.getTargets().get(5).getKind());
	}

}
