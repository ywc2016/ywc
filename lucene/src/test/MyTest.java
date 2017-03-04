package test;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import lucene.HelloLucene;

public class MyTest {
	@Test
	public void testIndex() {
		new HelloLucene().index();
	}

	@Test
	public void testSearch() throws ParseException {
		new HelloLucene().search("北航");
	}

	@Test
	public void testQueryNumOfDocs() {
		new HelloLucene().queryNumOfDocs();
	}

	@Test
	public void testDeleteDocs() {
		new HelloLucene().deleteDocuments();
	}
}
