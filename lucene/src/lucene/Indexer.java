package lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax.Type;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * ���飺 �ټ򵥵����ĵ���д������ ���ڸ���������ȡ�ĵ���
 * ������·�����ұ��������ĵ����ҵ����ؽ��
 */
public class Indexer {

	// д������ʵ����ָ��Ŀ¼��
	private IndexWriter writer;

	public Indexer() {

	}

	/**
	 * ���췽����Ϊ��ʵ����IndexWriter
	 */
	public Indexer(String indexDir) throws Exception {

		// File file=new File(indexDir);

		// �õ���������Ŀ¼��·��
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		// Directory dir = FSDirectory.open(file);

		// ʵ����������
		Analyzer analyzer = new StandardAnalyzer();

		// ʵ����IndexWriterConfig
		IndexWriterConfig con = new IndexWriterConfig(analyzer);

		// ʵ����IndexWriter
		writer = new IndexWriter(dir, con);

	}

	/**
	 * �ر�д����
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {

		writer.close();
	}

	/**
	 * ����ָ��Ŀ¼�������ļ�
	 * 
	 * @throws Exception
	 */
	public int index(String dataDir) throws Exception {

		// �����ļ����飬ѭ���ó�Ҫ���������ļ�
		File[] file = new File(dataDir).listFiles();

		for (File files : file) {

			// ���⿪ʼ����ÿ���ļ�������
			indexFile(files);
		}
		// ���������˶��ٸ��ļ����м����ļ����ؼ���
		int a = writer.numDocs();
		close();
		return a;

	}

	/**
	 * ����ָ���ļ�
	 * 
	 * @throws Exception
	 */
	private void indexFile(File files) throws Exception {

		System.out.println("�����ļ���" + files.getCanonicalPath());

		// ����Ҫһ��һ�е��ң�����������Ϊ�ĵ�������Ҫ�õ������У����ĵ�
		Document document = getDocument(files);

		// ��ʼд��,�Ͱ��ĵ�д���������ļ���ȥ�ˣ�
		writer.addDocument(document);

	}

	/**
	 * ����ĵ������ĵ��������������ֶ�
	 * filename��contents��fullpath�ֱ�洢���ƣ����ݺ�·��
	 * 
	 * @throws Exception
	 */
	private Document getDocument(File files) throws Exception {

		// ʵ����Document
		Document doc = new Document();

		// add():�����úõ������ӵ�Document��Ա���ȷ���������ĵ���
		doc.add(new Field("contents", new FileReader(files), TextField.TYPE_NOT_STORED));

		// Field.Store.YES�����ļ����������ļ��ΪNO��˵������Ҫ�ӵ������ļ���ȥ
		doc.add(new Field("filename", files.getName(), TextField.TYPE_STORED));

		// ������·�����������ļ���
		doc.add(new Field("fullPath", files.getCanonicalPath(), TextField.TYPE_STORED));

		// ����document
		return doc;
	}

	// ��ʼ����д������
	public static void main(String[] args) throws IOException, ParseException {

		// ����ָ�����ĵ�·��
		String indexDir = "directory";

		// ���������ݵ�·��
		String dataDir = "txt";

		// д����
		Indexer indexer = null;
		int numIndex = 0;

		// ������ʼʱ��
		long start = System.currentTimeMillis();

		try {
			// ͨ������ָ����·�����õ�indexer
			indexer = new Indexer(indexDir);

			// ��Ҫ����������·��(int:��Ϊ����Ҫ���������ݣ��ж��پͷ��ض��������������ļ�)
			numIndex = indexer.index(dataDir);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			// try {
			// indexer.close();
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		// ��������ʱ��
		long end = System.currentTimeMillis();

		// ��ʾ���
		System.out.println("������  " + numIndex + "  ���ļ���������  " + (end - start) + "  ����");

		Directory directory = FSDirectory.open(Paths.get(indexDir));
		DirectoryReader reader = DirectoryReader.open(directory);
		// ������
		IndexSearcher searcher = new IndexSearcher(reader);
		// �ҳ�filename���б��������ֵ�
		Term t = new Term("filename", "1");
		TermQuery query = new TermQuery(t);
		// QueryParser queryParser = new QueryParser("filename", new
		// StandardAnalyzer());
		// Query query = queryParser.parse("����");
		// ��õ÷ֿ�ǰ��3��ƥ���¼
		ScoreDoc[] docs = searcher.search(query, 3).scoreDocs;
		System.out.println("�����������");
		for (int i = 0; i < docs.length; i++) {
			String bookname = searcher.doc(docs[i].doc).get("filename");

			System.out.println("filename: " + bookname);
		}
		reader.close();
	}

}