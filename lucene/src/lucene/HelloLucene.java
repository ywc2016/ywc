package lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

public class HelloLucene {

	public void index() {
		// 1.创建Director
		// Directory directory = new RAMDirectory();// 创建在内存
		Directory directory = null;
		try {
			directory = FSDirectory.open(Paths.get("directory"));// 创建在硬盘
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		IndexWriter indexWriter = null;
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
		try {
			// 2.创建IndexWriter
			indexWriter = new IndexWriter(directory, indexWriterConfig);

			File dir = new File("txt");
			for (File file : dir.listFiles()) {
				// 3.创建Document
				Document document = new Document();
				// 4.为Document添加Field
				document.add(new Field("content", new FileReader(file), TextField.TYPE_NOT_STORED));
				document.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));
				document.add(new Field("path", file.getAbsolutePath(), TextField.TYPE_STORED));
				// 添加文档到索引
				indexWriter.addDocument(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void search(String keyWord) {

		try {
			// 1.创建Directory
			// 2.创建IndexReader
			Directory directory = FSDirectory.open(Paths.get("directory"));// 创建在硬盘
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			// 3.创建IndexSearcher
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
			// 4.创建搜索的Query
			QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());
			Query query = queryParser.parse(keyWord);

			// 5、根据searcher搜索并且返回TopDocs
			TopDocs topDocs = indexSearcher.search(query, 10);
			System.out.println("查找到的文档总共有：" + topDocs.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {

				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
				Document document = indexSearcher.doc(scoreDoc.doc);

				// 8、根据Document对象获取需要的值
				System.out.println(document.get("fileName") + " " + document.get("path"));
			}
			directoryReader.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
