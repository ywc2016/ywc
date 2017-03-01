package test;

import org.junit.Test;

import lucene.HelloLucene;

public class MyTest {
	@Test
	public void testIndex() {
		new HelloLucene().index();
	}

	@Test
	public void testSearch() {
		new HelloLucene().search("import");
	}

}
