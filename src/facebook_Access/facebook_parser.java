package facebook_Access;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import facebook_Entities.*;


public class facebook_parser {
	
	private static WebDriver facebookExtend = facebook.facebook;
	private static String curr_ele = facebook.current_ele_id;
	
	   public   List<WebElement> get_story_elements() throws InterruptedException 
	    {
		  
	        By ooo=  By.xpath("//div[contains(@class,'userContentWrapper')]");
	        List<WebElement> lst_elements= facebookExtend.findElements(ooo);
	        int i=0;
	        for(WebElement ele: lst_elements)
	        {
	             JavascriptExecutor jsExecutor5 = (JavascriptExecutor) facebookExtend;
	jsExecutor5.executeScript(
	  "var c=arguments[0].getElementsByTagName('*'); for(i=0;i<c.length;i++) {c[i].id='ele"+i+ "';}", ele);
	      i++;
	        }
	         

	     
	        return lst_elements;
	     }
	   
	   
	   
	   
	      public  FB_Post parse_post_data(WebElement ele, int element_index) throws InterruptedException
	      {
	          FB_Post obj_post=null;
	         
	              try
	              {
	                  
	                curr_ele="ele"+element_index;
	                element_index++;
	              
	                FB_User obj_user = new FB_User();
	              obj_post=new FB_Post();
	                obj_user.name=get_current_post_user_name(ele);
	                obj_user.is_verfied_user=is_verfied_user(ele);
	                obj_post.is_shared_posts=is_shared(ele,obj_user.name);
	                obj_post.curr_post_date=get_curr_post_time(ele);
	                   obj_post.curr_post_text=get_curr_post_text(ele);
	                obj_post.number_likes=get_number_post_likes(ele);
	                obj_post.number_angry=get_number_post_angry(ele);
	                obj_post.number_haha=get_number_post_haha(ele);
	                obj_post.number_loves=get_number_post_love(ele);
	                obj_post.number_sad=get_number_post_sad(ele);
	                obj_post.number_wow=get_number_post_wow(ele);
	                obj_post.number_comments=get_number_post_comments(ele);
	                obj_post.number_shares=get_number_post_shares(ele);
	                obj_post.external_URL=get_external_resource_in_post(ele);
	                obj_post.lst_Image_URLs=get_list_images_URL_in_post(ele);
	                obj_post.external_URL_Title=get_external_URL_title(ele);
	                obj_post.number_views=get_number_post_view(ele);
	                obj_user.is_page=is_page_responsibale_about_curr_post(ele); 
	                obj_post.has_video=has_video(ele);
	            
	                if( obj_post.is_shared_posts)
	                {
	                       FB_User obj_source_user = new FB_User();
	                       obj_source_user.name=get_source_post_user_name(ele);
	                       obj_source_user.is_verfied_user=source_post_is_verfied_user(ele);
	                       obj_source_user.is_page=is_page_responsiable_about_source_post(ele);
	                       obj_post.source_post_text=get_source_post_text(ele);
	                       obj_post.source_post_date=get_source_post_date(ele);
	                      obj_post.obj_source_post_user=obj_source_user;
	                   
	                }
	                
	                // obj_post.lst_comments=parse_comments_list(ele);
	                 obj_post.obj_curr_post_user=obj_user;
	    

	 }
	              catch(Exception ee)
	              {
	                  System.out.println("error");
	                  System.out.println(ee.getMessage() );
	                  ee.printStackTrace();
	              }
	          
	          
	          return obj_post;
	      }
	      
	      
	      
	       private WebElement modified_findBy_XPath_for_comments(WebElement e,String target_element,String commentID) throws InterruptedException
	      {
	         
	          if(target_element.contains("'ele'"))
	                  {
	                     target_element= target_element.replace("ele", commentID);
	                  }
	         
	          List<WebElement> lst=e.findElements(By.xpath(target_element));
	          
	          if(lst==null)
	              return null;
	          if(lst.size()==0)
	              return null;
	          return lst.get(0);
	      }
	       
	       
	       private WebElement modified_findBy_TagName(WebElement e,String target_element)
	      {
	          if(target_element.contains("'ele'"))
	                  {
	                     target_element= target_element.replace("ele", curr_ele);
	                  }
	          
	          List<WebElement> lst=e.findElements(By.tagName(target_element));
	          
	          if(lst==null)
	              return null;
	          
	          if(lst.size()==0)
	              return null;
	          return e.findElement(By.tagName(target_element));
	      }
	      
	      private   boolean has_video(WebElement ele)
	      {
	          WebElement video_tag= modified_findBy_TagName(ele,"video");
	          
	          if(video_tag==null)
	              return false;
	          
	          return true;
	      }
	      private   boolean is_page_responsiable_about_source_post(WebElement ele) throws InterruptedException
	      {
	          
	              WebElement div_ele= modified_findBy_XPath(ele, "//div[@id='ele' and "
	                  + "contains(@class,'mtm')]/span/span/a[contains(@href,'https://www.facebook.com')"
	                      + " and string-length(text())>0 and contains(@data-hovercard,'page.php') ]");
	           if(div_ele!=null)
	           {
	                
	                            return true;
	                       
	           }
	           
	          
	           
	           return false;
	      }
	      
	      private   boolean is_page_responsibale_about_curr_post(WebElement ele) throws InterruptedException
	      {
	          
	           WebElement h_5_ele=  modified_findBy_XPath(ele, "//h5[@id='ele']/span/span/span/a[contains(@data-hovercard,'page.php')]");

	           WebElement h_5_ele1=  modified_findBy_XPath(ele, "//h5[@id='ele']/span/span/a[contains(@data-hovercard,'page.php')]");

	           if(h_5_ele!=null)
	           {
	               return true;
	           }
	           
	           if(h_5_ele1!=null)
	           {
	               return true;
	           }
	           

	     
	              return  false;
	          
	      }
	      
	      private   void click_to_display_comments(WebElement ele) throws InterruptedException
	      {
	        WebElement a_ele=  modified_findBy_XPath(ele, "//a[@id='ele' and @role='button' and (contains(text(),'Comments') or contains(text(),'Comment'))]");
	          
	           if(a_ele!=null)
	           {
	                 a_ele.click();
	                 Thread.sleep(2000);
	  JavascriptExecutor jsExecutor5 = (JavascriptExecutor) facebookExtend;
	jsExecutor5.executeScript(
	    "var c=arguments[0].getElementsByTagName('*'); for(i=0;i<c.length;i++) {c[i].id='"+ curr_ele + "';}", ele);
	                       return;
	              
	           }
	               
	           
	           
	                 
	                     
	           
	      }
	      
	      
	      
	      private   List<FB_Comment> parse_comment_replies(WebElement ele ) throws InterruptedException
	       {
	            List<FB_Comment> lst_rep=new ArrayList<FB_Comment>();
	            
	            List<WebElement> a_eles=ele.findElements( By.name("a"));
	            
	            //exapnd replies
	            for(WebElement e:a_eles)
	             {
	                  if(e.getAttribute("class")!=null && e.getAttribute("href")!=null && e.getAttribute("role")!=null)
	                  {
	                      if(e.getAttribute("class").contains("UFICommentLink") && 
	                              e.getAttribute("href").contains("#") && e.getAttribute("role").contains("button"))
	                  {
	                      e.click();
	                      Thread.sleep(1000);
	                  }
	                  }
	             }
	            
	             List<WebElement> reply_eles=ele.findElements( By.xpath("//div[@aria-label='Comment reply']"));
	             
	             if(reply_eles!=null)
	             {
	                 for(WebElement rep:reply_eles)
	                 {
	                    // lst_rep.add(parse_on_comment(rep));
	                 }
	             }
	            
	            
	            return lst_rep;
	       }
	      
	      
	      
	      private   FB_Comment parse_on_comment(WebElement ele, String commentID ) throws InterruptedException
	       {
	            FB_Comment obj_comment= new FB_Comment();
	            WebElement found_ele;
	           found_ele=modified_findBy_XPath_for_comments(ele,"//a[@id='ele' and contains(@class,'UFICommentActorName')]",commentID);
	           
	          if(found_ele!=null)
	          {
	              obj_comment.user_name=found_ele.getText();
	          }
	          

	          found_ele=modified_findBy_XPath_for_comments(ele,"//a[@id='ele' and contains(@class,'UFICommentLikeButton') and contains(@aria-label,'Like')]",commentID);

	          if(found_ele!=null)
	          {
	              obj_comment.comment_likes= parse_action_string_to_long(found_ele.getAttribute("aria-label").split(" ")[0]);
	          }
	          
	          
	          
	          found_ele=modified_findBy_XPath_for_comments(ele,"//a[@id='ele' and contains(@role,'button') and contains(text(),'See More')]",commentID);

	          if(found_ele!=null)
	          {
	                 found_ele.click();
	                 Thread.sleep(1000);
	          }
	          
	          found_ele=modified_findBy_XPath_for_comments(ele,"//span[@id='ele' and contains(@class,'UFICommentBody')]",commentID);

	          if(found_ele!=null)
	          {
	                obj_comment.comment_text=found_ele.getText();
	          }
	          
	            found_ele=modified_findBy_XPath_for_comments(ele,"//abbr[@id='ele']",commentID);

	          if(found_ele!=null)
	          {
	           obj_comment.comment_date=Long.parseLong(found_ele.getAttribute("data-utime"));

	          }
	          
	         
	             
	             return obj_comment;
	             
	       }
	      private   void expand_comments_list(WebElement ele) throws InterruptedException
	      {
	          click_to_display_comments( ele);
	          
	         
	         
	         
	           for(int i=0;i<5;i++)
	           {
	          
	              
	             WebElement a_div=   modified_findBy_XPath(ele, "//a[@id='ele' and @role='button' and @class='UFIPagerLink' and contains(text(),'more comments')]");

	                  if(a_div!=null)
	                  {
	                      
	                              a_div.click();
	                              Thread.sleep(1000);
	                  }
	                  else
	                  {
	                      break;
	                  }
	                            
	               
	                System.out.println("expand num:"+i);
	           }
	           
	               JavascriptExecutor jsExecutor5 = (JavascriptExecutor) facebookExtend;
	jsExecutor5.executeScript(
	    "var c=arguments[0].getElementsByTagName('*'); for(i=0;i<c.length;i++) {c[i].id='"+ curr_ele + "';}", ele);



	           
	           
	           
	      }
	      
	      private   boolean source_post_is_verfied_user(WebElement ele) throws InterruptedException
	      {
	          
	            WebElement div_ele= modified_findBy_XPath(ele, "//div[@id='ele' and "
	                  + "contains(@class,'mtm')]/span/span/a[contains(@data-tooltip-content,'verified')]");
	           if(div_ele!=null)
	           {
	               return true;
	                
	           }
	           
	           return false;
	      }
	      
	       private   String get_source_post_text(WebElement ele) throws InterruptedException
	      {
	           
	           
	          
	             WebElement see_more_ele= modified_findBy_XPath(ele, "//div[@id='ele' and "
	                  + "contains(@class,'mtm')]/a[@class='see_more_link']");
	             
	             if(see_more_ele!=null)
	                {
	                    see_more_ele.click();
	                    Thread.sleep(1000);
	                }
	             
	              WebElement div_ele= modified_findBy_XPath(ele, "//div[@id='ele' and "
	                  + "contains(@class,'mtm')]/p");
	             
	             
	           if(div_ele!=null)
	           {
	               
	                  return  div_ele.getText();
	               
	                
	                
	           }
	           
	           return "";
	      }
	       
	       
	       private   long get_source_post_date(WebElement ele) throws InterruptedException
	      {
	           
	          WebElement div_ele= modified_findBy_XPath(ele, "//div[@id='ele' and @class='mtm']");
	           if(div_ele!=null)
	           {
	                WebElement par_ele=ele.findElement( By.xpath(".."));
	                
	                WebElement abbr_eles=modified_findBy_TagName(par_ele, "abbr");
	                
	                if(abbr_eles!=null)
	                {
	                    try
	                    {
	                    Long.parseLong(abbr_eles.getAttribute("data-utime"));
	                    }
	                    catch(Exception e)
	                    {
	                        
	                    }
	                }
	                
	           }
	           
	           return -1;
	      }
	      
	      private   String get_source_post_user_name(WebElement ele) throws InterruptedException
	      {
	           
	          WebElement div_ele= modified_findBy_XPath(ele, "//div[@id='ele' and "
	                  + "contains(@class,'mtm')]/span/span/a[contains(@href,'https://www.facebook.com') and string-length(text())>0]");
	           if(div_ele!=null)
	           {
	                
	                            return div_ele.getText();
	                       
	           }
	           
	           return "not found";
	      }
	      private   String get_external_URL_title(WebElement ele)throws InterruptedException
	      {
	          
	            WebElement external_URL= modified_findBy_XPath(ele, "//a[@id='ele'  "
	                + "and contains(@onclick,'Link') and contains(@onmouseover,'Link') and string-length(text())>0]");
	        

	        if(external_URL!=null)
	        {
	             return external_URL.getText();
	        }
	        
	        
	         
	           
	           return "";
	      }
	      
	      private   List<String> get_list_images_URL_in_post(WebElement ele)
	      {
	       List<WebElement> imgs= ele.findElements(By.xpath("//a[@id='ele']/div/img[contains(@src,'https://scontent.xx.fbcdn.net') or contains(@src,'https://external.xx.fbcdn.net') ]".replace("ele", curr_ele)));
	          
	        List<String> lst_img_URLs= new ArrayList<String>();

	           
	         for(WebElement e1:imgs)
	          {
	                                 
	             lst_img_URLs.add(e1.getAttribute("src"));
	                                    
	                                 
	           }
	                 
	           
	           return lst_img_URLs; 
	      }
	      
	      
	      private   String get_external_resource_in_post(WebElement ele)throws InterruptedException
	      {
	       
	        WebElement external_URL= modified_findBy_XPath(ele, "//a[@id='ele' and not(contains(@href,'facebook')) "
	                + "and contains(@onclick,'Link') and contains(@onmouseover,'Link')] ");
	        

	        if(external_URL!=null)
	        {
	             return external_URL.getAttribute("href");
	        }
	        
	       
	           
	           return "";
	           
	           
	      }
	      
	      private   long get_number_post_view(WebElement ele)throws InterruptedException
	      {
	          
	            WebElement num_shares= modified_findBy_XPath(ele, "//span[@id='ele' and (contains(text(),'Views') or contains(text(),'View'))]");

	           
	                     if(num_shares!=null)
	                     {
	                        
	                         String num[]=num_shares.getText().trim().split(" ");
	                         
	                         if(num.length>1)
	                         {
	                              return parse_action_string_to_long(num[0]);
	                         }
	                     }
	                     
	             return 0;
	             
	             
	          
	      }
	      
	       private   long get_number_post_shares(WebElement ele)throws InterruptedException
	      {
	     WebElement num_shares= modified_findBy_XPath(ele, "//a[@id='ele' and (contains(text(),'Shares') or contains(text(),'Share'))]");

	           
	                     if(num_shares!=null)
	                     {
	                        
	                         String num[]=num_shares.getText().trim().split(" ");
	                         
	                         if(num.length>1)
	                         {
	                              return parse_action_string_to_long(num[0]);
	                         }
	                     }
	                     
	             return 0;
	            
	      }
	       
	      
	      private   long get_number_post_comments(WebElement ele)throws InterruptedException
	      {
	          
	           WebElement view_more_comments= modified_findBy_XPath(ele, "//a[@id='ele' and contains(@class,'UFIPagerLink') and contains(@role,'button') and contains(text(),'more comments')]");
	          
	           if(view_more_comments!=null)
	           {
	               if(!view_more_comments.getText().toLowerCase().equals("view more comments"))
	               {
	                   try
	                   {
	                   String num=view_more_comments.getText().split(" ")[1];
	                   return Long.parseLong(num.replace(",", ""))+2;
	                   }
	                   catch (Exception e)
	                           {
	                               
	                           }
	               }
	           }
	           
	          WebElement pager_comments= modified_findBy_XPath(ele, "//span[@id='ele' and contains(@class,'UFIPagerCount') and contains(text(),'of')]");
	          
	          if(pager_comments!=null)
	          {
	               try
	                   {
	              String num=pager_comments.getText().split(" ")[2];
	              return Long.parseLong(num.replace(",", ""));
	                   }
	              catch (Exception e)
	                           {
	                               
	                           }
	          }
	           
	          
	          WebElement a_comments= modified_findBy_XPath(ele, "//span[@id='ele' and (contains(text(),'Comments') or contains(text(),'Comment'))]");

	          if(a_comments!=null)
	          {
	              String num=a_comments.getText().trim().split(" ")[0];
	                         
	               return parse_action_string_to_long(num);
	          }
	          
	          
	          WebElement a_comments1= modified_findBy_XPath(ele, "//a[@id='ele' and (contains(text(),'Comments') or contains(text(),'Comment'))]");

	          if(a_comments1!=null)
	          {
	              String num=a_comments1.getText().trim().split(" ")[0];
	                         
	               return parse_action_string_to_long(num);
	          }
	          
	          
	          
	       
	         
	             return 0;
	      }
	      
	      private   long get_number_post_likes(WebElement ele)throws InterruptedException
	      {
	           WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Like') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);      
	                         
	                       
	             
	             
	      }
	      
	       private   long get_number_post_love(WebElement ele)throws InterruptedException
	      {
	          
	           WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Love') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);  
	            
	            
	           
	      }
	       
	       
	        private   long get_number_post_haha(WebElement ele)throws InterruptedException
	      {
	          
	             WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Haha') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);  
	            
	            
	           
	      }
	        
	        
	        private   long get_number_post_sad(WebElement ele)throws InterruptedException
	      {
	              WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Sad') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);  
	      }
	        
	        private   long get_number_post_wow(WebElement ele)throws InterruptedException
	      {
	              WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Wow') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);  
	      }
	        
	        
	        private   long get_number_post_angry(WebElement ele)throws InterruptedException
	      {
	             WebElement ele_span= modified_findBy_XPath(ele,"//a[@id='ele' and contains(@aria-label,'Angry') and contains(@role,'button')]");
	   
	           if(ele_span==null)
	               return 0;
	              
	            String num=ele_span.getAttribute("aria-label").toLowerCase().split(" ")[0];
	                         
	            return parse_action_string_to_long(num);  
	            
	            
	         
	      }
	    
	    
	    
	    public  static  String get_current_post_user_name(WebElement ele)throws InterruptedException
	    {
	       
	         WebElement h_5_ele=  modified_findBy_XPath(ele, "//h5[@id='ele']/span/span/span/a");

	         WebElement h_5_ele1=  modified_findBy_XPath(ele, "//h5[@id='ele']/span/span/a");

	         if(h_5_ele!=null)
	         {
	             return h_5_ele.getText();
	         }
	         
	         if(h_5_ele1!=null)
	         {
	             return h_5_ele1.getText();
	         }
	         
	         return "";
	         
//	      
	  
	    }
	    
	    private static WebElement modified_findBy_XPath(WebElement e,String target_element) throws InterruptedException
	    {
	        if(target_element.contains("'ele'"))
	                {
	                   target_element= target_element.replace("ele", curr_ele);
	                }
	        
	        List<WebElement> lst=e.findElements(By.xpath(target_element));
	        
	        if(lst==null)
	            return null;
	        if(lst.size()==0)
	            return null;
	        return lst.get(0);
	    }
	    
	    

		   public static  void do_scrolling( int numbr_of_scorls, long sleep_time_milli_sec) throws InterruptedException
		    {
		        for(int i=0;i<numbr_of_scorls;i++)
		        {
		        JavascriptExecutor js = ((JavascriptExecutor) facebookExtend);

		          js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		          
		           Thread.sleep(sleep_time_milli_sec);
		        }
		        
		    }
		   
		   
		   
		   
		   
		    private   boolean is_verfied_user(WebElement ele)throws InterruptedException
		      {
		         WebElement span_ele= modified_findBy_XPath(ele, "//span[ @id='ele' and contains(@data-tooltip-content,'Verified') "
		                 + "and @data-hover='tooltip' and @data-tooltip-position='right']");

		         
		          
		           if (span_ele!=null)
		               
		           {
		                 return true;
		                     
		           }
		           
		           return false;
		      }
		      
		      
		      private   boolean is_shared(WebElement ele, String user_name)throws InterruptedException
		      {
		          
		          
		          WebElement h_5_ele=modified_findBy_XPath(ele, "//h5[@id='ele']");
		         
		     
		           
		           if (h_5_ele!=null)
		               
		           {
		                String remove_out_name=h_5_ele.getText().replace(user_name, "");
		                
		                if(remove_out_name.contains("shared"))
		                {
		                    return true;
		                }
		               
		           }
		           
		           return false;
		      }
		      
		      
		      private   String get_curr_post_text(WebElement ele) throws InterruptedException
		      {
		          

		        WebElement user_txt_div=   modified_findBy_XPath(ele, "//div[@id='ele' and contains(@class,'userContent')]");

		        
		        if(user_txt_div!=null)
		        {
		           
		             List<WebElement> user_txt_p= user_txt_div.findElements(By.tagName("p"));
		             String text="";
		             for(WebElement e_p:user_txt_p)
		             {
		                 text+=e_p.getText();
		             }
		             
		        
		             
		            return text;
		            
		        }
		            

		             return "not found";

		  
		    }
		     
		      
		       public void setAttribute(WebElement element, String attName, String attValue) {
		           JavascriptExecutor jsExecutor5 = (JavascriptExecutor) facebookExtend;
		 jsExecutor5.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
		        
		        
		 
		       }
		       
		      private   long get_curr_post_time(WebElement ele)throws InterruptedException
		      {
		          WebElement h_5_ele= modified_findBy_XPath(ele, "//h5[@id='ele']");
		          if(h_5_ele!=null)
		          {
		          WebElement parent_div= h_5_ele.findElement(By.xpath(".."));
		          WebElement arr_ele= modified_findBy_TagName(parent_div,"abbr");
		           
		                   if(arr_ele!=null)
		                   {
		                      if(arr_ele.getAttribute("data-utime")!=null)
		                        {
		                            try
		                            {
		                                 return Long.parseLong(arr_ele.getAttribute("data-utime"));
		                            }
		                            catch(Exception e)
		                            {
		                                
		                            }
		                            
		                        }
		                   }    
		          }
		           return -1;
		     
		      }


		      private   long parse_action_string_to_long(String num)
		      {
		          try
		          {
		              if(num.contains("K") || num.contains("k")) 
		                         {
		                             num=num.replace("K", "").replace("k", "").trim();
		                           return  (long)(Double.parseDouble(num)*1000);
		                         }else if(num.contains("M")|| num.contains("m"))
		                         {
		                              num=num.replace("M", "").replace("m", "").trim();
		                             return  (long)(Double.parseDouble(num)*1000000);
		                         }
		                        else
		                         {
		                             return Long.parseLong(num);
		                         } 
		          }
		          catch(Exception e)
		          {
		              return -1;
		          }
		          
		      }
		      
	    
	 
	       
	    
	    

}
