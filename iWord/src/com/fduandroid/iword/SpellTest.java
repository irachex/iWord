package com.fduandroid.iword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fduandroid.iword.iWord.Word;

public class SpellTest extends Activity {
	
	private TextView spellListName;
	private TextView spellMeaning;
	private TextView spellWord;
	private TextView spellHint;
	private EditText spellText;
	private Button spellShowAns;
	private ImageButton spellNext;
	private ImageButton spellBackToHome;

	private java.util.Random random=new java.util.Random();
	private int wordId;
    private Word word;
    
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
	
	protected void updateDisplay(Word word) {
        spellMeaning.setText(word.Meaning);
        spellWord.setText("");
        spellHint.setText("单词长度为"+word.Word.length()+"位，已输入0位");
        spellText.setText("");
        spellText.append(word.Word.subSequence(0, 1));
        spellText.requestFocus();
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(word.Word.length());
        spellText.setFilters(filters);
    }

	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.spelltest);
		try {
        initLayout();
		} catch (Exception e) {
		    System.out.println(e);
		}
    	 word=getNextWord();
       	 updateDisplay(word);
    }
    
    private void initLayout() {
    	  spellListName=(TextView)findViewById(R.id.spellListName);
          spellListName.setText(getPref(iWord.PREFS_NAME,iWord.LIST_FULL_NAME,"大学英语四级"));
          
          spellMeaning=(TextView)findViewById(R.id.spellMeaning);
          spellWord=(TextView)findViewById(R.id.spellWord);
          
          spellNext=(ImageButton)findViewById(R.id.spellNext);
          spellNext.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
           	     word=getNextWord();
           	     updateDisplay(word);
              }
          });
          
          spellShowAns=(Button)findViewById(R.id.spellShowAns);
          spellShowAns.setOnClickListener(new View.OnClickListener() {
        	  public void onClick(View v) {
        		  spellWord.setText(word.Word.toString());
        	  }
          });
          
          spellHint=(TextView)findViewById(R.id.spellHint);
          
          spellText=(EditText)findViewById(R.id.spellText);
          spellText.setSingleLine();
          spellText.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before,	int count) {
			        spellHint.setText("单词长度为"+word.Word.length()+"位，已输入"+s.length()+"位");
			        if (s.length()==word.Word.length()){
			        	if (word.Word.contentEquals(s)) {
			            	spellHint.setText("回答正确!");
			        		spellWord.setText(word.Word.toString());
			           }
			        	else {
			        	    spellHint.setText("单词长度为"+word.Word.length()+"位，拼写错误");
			        	}
			        }
			    }

				public void afterTextChanged(Editable s) {
				}

				public void beforeTextChanged(CharSequence s, int start,int count, int after) {
				}
          });
          
          spellBackToHome=(ImageButton)findViewById(R.id.spellBackToHome);
          spellBackToHome.setOnClickListener(new View.OnClickListener() {
        	  public void onClick(View v) {
        		   Intent  it=new Intent();
                  it.setClass(SpellTest.this, iWord.class);
                  startActivity(it);
                  SpellTest.this.finish();
        	  }
          });    
      }
}
