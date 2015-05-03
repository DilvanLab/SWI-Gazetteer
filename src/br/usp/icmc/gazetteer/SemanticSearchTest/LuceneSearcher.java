/*    This file is part of SWI Gazetteer.

    SWI Gazetteer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SWI Gazetteer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SWI Gazetteer.  If not, see <http://www.gnu.org/licenses/>.
    */
package br.usp.icmc.gazetteer.SemanticSearchTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author Silvio  
 */

public class LuceneSearcher {
    private static int numQ=126;
    private long numDoc;
    private int topK;
    private static ArrayList<Documento> documentospesquisados = new ArrayList<Documento>();
        
    private  IndexWriter writer;
    private  Directory dir;
    private  Analyzer a;
    
    public LuceneSearcher(String diretorio, String salvar,int topK,int stopword,String path) throws IOException, Exception {
        numQ=1;
        this.topK = topK;
        dir = new RAMDirectory();
       // dir = new SimpleFSDirectory(new File("C:\\Users\\Silvio\\index\\"));
        a = new StandardAnalyzer(Version.LUCENE_36);
        System.out.println("Sem stop word");
       
      
    }

    public static int getNumQ() {
        return numQ;
    }

    public static void setNumQ(int numQ) {
        LuceneSearcher.numQ = numQ;
    }

    public long getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(long numDoc) {
        this.numDoc = numDoc;
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }
 
    public long getNumdocs(){
        return this.numDoc;
    }
    public static ArrayList<Documento> getDoc(){
        return documentospesquisados;
    }
    
    public void addDocument(File f) throws FileNotFoundException, IOException {
        Document d = new Document();
        
        d.add(new Field("content", new FileReader(f)));
        d.add(new Field("filename", f.getName(), Field.Store.YES,Field.Index.ANALYZED));
        writer.addDocument(d);
    }
    
    public void indexDirectory(File path) throws FileNotFoundException, IOException {
        File files[]; 
        files = path.listFiles();
        for(int i = 0, n = files.length; i < n; i++){
            if(files[i].isFile())
               this.addDocument(new File(files[i].getAbsolutePath()));
            if(files[i].isDirectory()){
                indexDirectory(files[i]);
            }
        }
    }
    
    public void close() throws IOException {
        numDoc =writer.numDocs();
        writer.close();
    }
    public void similiaridade1() throws IOException{
       
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, a);
        conf.setSimilarity(similarityltc());
        writer = new IndexWriter(dir, conf);
    }
    
    public void similaridade2() throws IOException{
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, a);
        conf.setSimilarity(similaritylnc());
        writer = new IndexWriter(dir, conf);
    }
       public void similaridade3() throws IOException{
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, a);
        conf.setSimilarity(similaritynnc());
        writer = new IndexWriter(dir, conf);
    }
    public DefaultSimilarity similarityltc(){
      return new DefaultSimilarity() {
            @Override
            public float tf(float f) {
                return (float) Math.sqrt(f);
            }
            
             public float idf(int docFreq, int numDocs) {
              return super.idf(docFreq, numDocs); //To change body of generated methods, choose Tools | Templates.
          }
            public float idf(long docFreq, long numDocs) {
                  return (float) (Math.log(numDocs / (double) (docFreq+1)) + 1);
            }
        };
          
    }
    public DefaultSimilarity similaritylnc(){
      return new DefaultSimilarity() {
            @Override
            public float tf(float f) {
                return (float) (1 + Math.log(f));
            }
            
            
          public float idf(int docFreq, int numDocs) {
              return super.idf(docFreq, numDocs); //To change body of generated methods, choose Tools | Templates.
          }
            public float idf(long docFreq, long numDocs) {
                return 1;
            }
        };
    }
    public DefaultSimilarity similaritynnc(){
      
      return new DefaultSimilarity() {
            @Override
            public float tf(float f) {
                return (float) f;
            }
            
             public float idf(int docFreq, int numDocs) {
              return super.idf(docFreq, numDocs); //To change body of generated methods, choose Tools | Templates.
          }
            public float idf(long docFreq, long numDocs) {
                return 1;
            }
        };
    }
    public void busca(IndexSearcher reader,String q) throws ParseException, IOException{
       
   QueryParser parser = new QueryParser(Version.LUCENE_36, "content", a);
    Query query = parser.parse(q);
     TopDocs hits = reader.search(query, topK);
    ScoreDoc[] docs = hits.scoreDocs;
    // Iterate through the results:
    int i=0;
    for(ScoreDoc d : docs){
            
        Document doc = reader.doc(d.doc);
            documentospesquisados.add(new Documento(numQ,doc.get("filename"),0));
        }

    }
    
    public void search1(String q) throws IOException, ParseException {
        IndexReader ireader = IndexReader.open(dir); // read-only=true
        IndexSearcher reader = new IndexSearcher(ireader);
        reader.setSimilarity(similarityltc()); //ddd.qqq
        busca(reader,q);
        numQ++;
    }   
}
