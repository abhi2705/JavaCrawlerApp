package com.crawler;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mortbay.util.ajax.JSON;


/**
 * Servlet implementation class Query
 */
public class CrawlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static List<String> revList = new ArrayList<String>();
	static List<String> dateList = new ArrayList<String>();
	static List<String> titleList = new ArrayList<String>();
	static List<String> starList = new ArrayList<String>();   
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrawlerServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }

    	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		

	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String baseUrl = request.getParameter("url");
		String pageValue = request.getParameter("pageValue");
		out.println("Url ="+baseUrl+pageValue);
		
		
		//String baseUrl = "http://www.flipkart.com/samsung-galaxy-note-2-n7100/product-reviews/ITMDHM3NUFYRRQKP?pid=MOBDDPH4CUB2Q3FU&rating=1,2,3,4,5&reviewers=all&type=top&sort=most_helpful&start=";
		String temp;
		//System.out.println(baseUrl);
		//int i = Integer.parseInt(name);
		//int i=380;
		int i = Integer.parseInt(pageValue);
		
		JSONObject obj = new JSONObject();
		while(true)
			{
				temp = baseUrl + i;
				System.out.println(temp);
				out.println(temp);
				if(DataFunnel(temp).equals(""))
					{	
						System.out.println("No More pages to crawl");
			        	System.out.println("Stop Page Link = "+temp);
			        	System.out.println("byeeeeeeeeeeeeee");
			        	break;
			        }
				i=i+10;
			}
		

		try {
			obj.put("RatingStars-List",   starList);
			obj.put("Reviews",  revList);
			obj.put("Review-Date",  dateList);
			obj.put("Review-Title",  titleList);
			
			System.out.println(obj.get("Review-Date"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
//		PrintWriter writerjson = new PrintWriter(new FileWriter("Note2-JSONData.json", true));
//		writerjson.println(obj);
//		writerjson.close();
		out.println("Hello World1");

		out.println(starList);
		out.println("-------------------------------------- The Final JSON is --------------------------------------");
		out.println(obj);
			
		
		
		
		
	}

	public static String DataFunnel(String link) {
	    org.jsoup.nodes.Document doc;
	    Elements reviews = null;
	    //String urlNextPage = new String();
	    try {
//	    	PrintWriter writer = new PrintWriter(new FileWriter("note2.txt", true));
	        
	    	// need http protocol
	        doc = Jsoup.connect(link).timeout(25000).get();
	        reviews = doc.select("span[class=review-text]");
	        Elements date = doc.select("div[class=date line fk-font-small]");
	        Elements reviewHeading = doc.select("div[class=line fk-font-normal bmargin5 dark-gray]");
	        String title = doc.title();
	        Elements s = doc.select("div[class=fk-stars]");
	        Element tempelement;
	        for(int i = 0;i<s.size();i++)
	        {
	            tempelement = s.get(i);
	            starList.add(tempelement.attr("title").replaceAll(" stars", ""));
	            dateList.add(((Element) date.toArray()[i]).text());
	            revList.add(((Element) reviews.toArray()[i]).text());
	            titleList.add(((Element) reviewHeading.toArray()[i]).text());
	        }
	        
	        
//	        System.out.println("-----------------------Star Lists --------" + starList);
//	        
//	        Elements datess = doc.select("div[class=date line fk-font-small]");
//	        dateList.add(((Element) datess.toArray()[2]).text());
//	        System.out.println(((Element) datess.toArray()[2]).text());
	        
	        //System.out.println("-----------------------Reviews --------" + reviews);
	        //System.out.println("-----------------------Date --------" + date);
	        //System.out.println("-----------------------ReviewHeading --------" + reviewHeading);
	        
//	        writer.println("Page Title : " + title);
//	        writer.println("PresentPageURL = "+ link);
//	        writer.println("-----------------------Reviews --------" + reviews);
//	        writer.println("-----------------------Date --------" + date);
//	        writer.println("-----------------------ReviewHeading --------" + reviewHeading);
	        
	        
	        
	        //urlNextPage = doc.select("a.nav_bar_next_prev").first().attr("abs:href");
	        //This is creating some issues, the loop ends coz of this
	        
	        
	        //System.out.println("NextPageURL = "+ urlNextPage);
	        
	        
	        
	        
	        //writer.println("NextPageURL = "+ urlNextPage);
	        
	        //Elements productList = doc.select("div[class=date line fk-font-small]");
	        //System.out.println("-----------------------Date --------" + productList);
	        // get page title
	        //System.out.println("title : " + title);

//	        writer.close();
	        
	        // get all links
//	        Elements links = doc.select("a[href]");
//	        for (Element link : links) {
//	            // get the value from href attribute
//	            System.out.println("\nlink : " + link.attr("href"));
//	            System.out.println("text : " + link.text());
//	        }

	    } 
//	    catch(NullPointerException n)
//        {
//        	System.out.println("No More pages to crawl");
//        	System.out.println("Last Page Crawled = "+link);
//        	System.exit(0);
//        }
	    
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return (reviews.toString());
        
	}

	
}

