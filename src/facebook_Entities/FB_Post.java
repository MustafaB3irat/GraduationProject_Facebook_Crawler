package facebook_Entities;

import java.util.List;

public class FB_Post {
	
	 public FB_User obj_curr_post_user;

	  public  FB_User obj_source_post_user;//in case of shared post

	    public long number_likes;
	 
	  public  long number_loves;
	
	   public long number_haha;
	

	  public  long number_wow;


	 public   long number_sad;
	 
	
	 public   long number_angry;
	 

	 public boolean has_video;
	 

	 public   long number_comments;


	  public  long number_views;

	

	  public  long number_shares;
	 


	  public  String curr_post_text;

	
	  public  String source_post_text; //in case of shared post

	  public  long curr_post_date;


	  public  long source_post_date; // in case of shraed post

	  public  boolean is_shared_posts;
	
	  public List<String> lst_Image_URLs;
	   
	  public String external_URL;
	 
	 
	  public String external_URL_Title;
	    
	  public List<FB_Comment> lst_comments;
	  
	  public double post_prior_probabilty;

	public FB_Post(FB_User obj_curr_post_user, FB_User obj_source_post_user, long number_likes, long number_loves,
			long number_haha, long number_wow, long number_sad, long number_angry, boolean has_video,
			long number_comments, long number_views, long number_shares, String curr_post_text, String source_post_text,
			long curr_post_date, long source_post_date, boolean is_shared_posts, List<String> lst_Image_URLs,
			String external_URL, String external_URL_Title, List<FB_Comment> lst_comments,
			double post_prior_probabilty) {
		super();
		this.obj_curr_post_user = obj_curr_post_user;
		this.obj_source_post_user = obj_source_post_user;
		this.number_likes = number_likes;
		this.number_loves = number_loves;
		this.number_haha = number_haha;
		this.number_wow = number_wow;
		this.number_sad = number_sad;
		this.number_angry = number_angry;
		this.has_video = has_video;
		this.number_comments = number_comments;
		this.number_views = number_views;
		this.number_shares = number_shares;
		this.curr_post_text = curr_post_text;
		this.source_post_text = source_post_text;
		this.curr_post_date = curr_post_date;
		this.source_post_date = source_post_date;
		this.is_shared_posts = is_shared_posts;
		this.lst_Image_URLs = lst_Image_URLs;
		this.external_URL = external_URL;
		this.external_URL_Title = external_URL_Title;
		this.lst_comments = lst_comments;
		this.post_prior_probabilty = post_prior_probabilty;
	}

	public FB_Post() {
		super();
	}
	  
	  
	  
	
	

}
