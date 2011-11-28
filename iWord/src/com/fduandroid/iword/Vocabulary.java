package com.fduandroid.iword;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import com.fduandroid.iword.iWord.Word;

public class Vocabulary extends Activity {

    static final int PROGRESS_DIALOG = 0;
    
	private TextView reciteListName;
	private TextView reciteWord;
	private TextView reciteMeaning;
	private ImageButton reciteNext;
	private ImageButton backToHome;

	private java.util.Random random=new java.util.Random();
	private int wordId;

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
	
	protected void updateDisplay(iWord.Word word) {
        reciteWord.setText(word.Word);
        reciteMeaning.setText(word.Meaning);
	}

	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.recite);
		
        initLayout();
    	 Word word=getNextWord();
       	 updateDisplay(word);
    }
    
    private void initLayout() {
    	  reciteListName=(TextView)findViewById(R.id.reciteListName);
          reciteListName.setText(getPref(iWord.PREFS_NAME,iWord.LIST_FULL_NAME,"大学英语四级"));
          
          reciteWord=(TextView)findViewById(R.id.reciteWord);
          reciteMeaning=(TextView)findViewById(R.id.reciteMeaning);
          
          reciteNext=(ImageButton)findViewById(R.id.reciteNext);
          reciteNext.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
           	     Word word=getNextWord();
           	     updateDisplay(word);
              }
          }); 
          
          backToHome=(ImageButton)findViewById(R.id.backToHome);
          backToHome.setOnClickListener(new View.OnClickListener() {
        	  public void onClick(View v) {
        		   Intent  it=new Intent();
                  it.setClass(Vocabulary.this, iWord.class);
                  startActivity(it);
                  Vocabulary.this.finish();
        	  }
          });
    }
}
