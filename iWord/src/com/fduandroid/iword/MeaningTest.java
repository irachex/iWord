package com.fduandroid.iword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import com.fduandroid.iword.iWord.Word;

public class MeaningTest extends Activity {
    
	private TextView meaningListName;
	private TextView meaningWord;
	private TextView meaning;
	private ImageButton meaningNext;
	private Button meaning1;
	private Button meaning2;
	private Button meaning3;
	private Button meaning4;
	private ImageButton meaningbackToHome;

	private java.util.Random random=new java.util.Random();
	private int wordId;

	static int rightId=0;
	static String rightMeaning;
	
    public void setPref(String PrefName,String key,String value) {
	    SharedPreferences settings=getSharedPreferences(PrefName,0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(key, value);
	    editor.commit();
    }
    
    public String getPref(String PrefName,String key,String defValue) {
 	    SharedPreferences settings=getSharedPreferences(PrefName,0);
 	    String ln=settings.getString(key, defValue);
 	    return ln;
    }

	public Word getNextWord() {
		 String isRan=getPref(iWord.PREFS_NAME,iWord.IS_RANDOM,"true");
		 if (isRan=="true") return iWord.wordList.get(random.nextInt(iWord.wordList.size()));
		 else  return iWord.wordList.get((wordId++)%iWord.wordList.size());
    }
	public String getRandomMeaning() {
	    return iWord.wordList.get(random.nextInt(iWord.wordList.size())).Meaning;
	}
	
	protected void updateDisplay(iWord.Word word) {
        meaningWord.setText(word.Word);
        meaning.setText("");
        meaning1.setText(getRandomMeaning());
        meaning2.setText(getRandomMeaning());
        meaning3.setText(getRandomMeaning());
        meaning4.setText(getRandomMeaning());
  	    meaning1.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn));  
  	    meaning2.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn));  
  	    meaning3.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn));  
  	    meaning4.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn));  
  	    meaningNext.setVisibility(View.INVISIBLE);
        rightId=random.nextInt(4)+1;
        switch (rightId) {
        case 1:meaning1.setText(word.Meaning); break;
        case 2:meaning2.setText(word.Meaning); break;
        case 3:meaning3.setText(word.Meaning); break;
        case 4:meaning4.setText(word.Meaning); break;
        default:
        }
        rightMeaning=word.Meaning;
	}

	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.meaningtest);
		
        initLayout();
    	 Word word=getNextWord();
       	 updateDisplay(word);
    }
    
    private void initLayout() {
    	 meaningListName=(TextView)findViewById(R.id.meaningListName);
         meaningListName.setText(getPref(iWord.PREFS_NAME,iWord.LIST_FULL_NAME,"大学英语四级"));
           	
 
    	  meaningWord=(TextView)findViewById(R.id.meaningWord);
    	  meaning=(TextView)findViewById(R.id.meaning);

    	  meaningNext=(ImageButton)findViewById(R.id.meaningNext);
    	  meaningNext.setOnClickListener(new View.OnClickListener() {
    		  public void onClick(View v) {
    			  Word word=getNextWord();
    			  updateDisplay(word);
    		  }
    	  });
         
    	  meaning1=(Button)findViewById(R.id.meaning1);
    	  meaning1.setOnClickListener(new View.OnClickListener() {
    		  public void onClick(View v) {
    		      if  (rightId==1) {
    		    	  meaning1.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_correct));
    		    	  meaning.setText(rightMeaning);
    		    	  meaningNext.setVisibility(View.VISIBLE);
    		      }
    		      else {
    		    	  meaning1.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_wrong));  
    		      }
    		  }
    	  });
    	  
    	  meaning2=(Button)findViewById(R.id.meaning2);
       	  meaning2.setOnClickListener(new View.OnClickListener() {
    		  public void onClick(View v) {
    		      if  (rightId==2) {
    		    	  meaning2.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_correct));
    		    	  meaning.setText(rightMeaning);
    		    	  meaningNext.setVisibility(View.VISIBLE);
    		      }
    		      else {
    		    	  meaning2.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_wrong));  
    		      }
    		  }
    	  });
       	  
    	  meaning3=(Button)findViewById(R.id.meaning3);
       	  meaning3.setOnClickListener(new View.OnClickListener() {
    		  public void onClick(View v) {
    		      if  (rightId==3) {
    		    	  meaning3.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_correct));
    		    	  meaning.setText(rightMeaning);
    		    	  meaningNext.setVisibility(View.VISIBLE);
    		      }
    		      else {
    		    	  meaning3.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_wrong));  
    		      }
    		  }
    	  });
       	  
    	  meaning4=(Button)findViewById(R.id.meaning4);
       	  meaning4.setOnClickListener(new View.OnClickListener() {
    		  public void onClick(View v) {
    		      if  (rightId==4) {
    		    	  meaning4.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_correct));
    		    	  meaning.setText(rightMeaning);
    		    	  meaningNext.setVisibility(View.VISIBLE);
    		      }
    		      else {
    		    	  meaning4.setBackgroundDrawable(getResources().getDrawable(R.drawable.meaningbtn_wrong));  
    		      }
    		  }
    	  });
       	  
       	 meaningbackToHome=(ImageButton)findViewById(R.id.meaningbackToHome);
         meaningbackToHome.setOnClickListener(new View.OnClickListener() {
      	  public void onClick(View v) {
      		   Intent  it=new Intent();
                it.setClass(MeaningTest.this, iWord.class);
                startActivity(it);
                MeaningTest.this.finish();
      	  }
        });
    }
}
