package facebook_Entities;

import java.util.List;

public class FB_Comment {
	
	 public String comment_text; 
	 public  long comment_date;
	 public  long comment_likes;
	 public long comment_replies;
	 public String user_name; 
	 public List<FB_Comment> lst_replies;
	 
	 
	 
	public FB_Comment(String comment_text, long comment_date, long comment_likes, long comment_replies,
			String user_name, List<FB_Comment> lst_replies) {
		super();
		this.comment_text = comment_text;
		this.comment_date = comment_date;
		this.comment_likes = comment_likes;
		this.comment_replies = comment_replies;
		this.user_name = user_name;
		this.lst_replies = lst_replies;
	}



	public FB_Comment() {
	
	}
	 
	 
	    
	


}
