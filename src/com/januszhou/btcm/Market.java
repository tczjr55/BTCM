package com.januszhou.btcm;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.januszhou.btcm.HttpRequest;


public class Market {
	
	public static final String TAG = "Market";
	public MarketData marketDataArray[];
	
	
	public huobiMarket huobi;
	public okcoinMarket okcoin;
	public bitstampMarket bitstamp;
	public btcChinaMarket btcc;
	
	public Market(){
		huobi = new huobiMarket();
		okcoin = new okcoinMarket();
		bitstamp = new bitstampMarket();
		//btcc = new btcChinaMarket();
		marketDataArray = new MarketData[BtcmApplication.MARKET_NUM];
		marketDataArray[0] = bitstamp.getMarketData();
		marketDataArray[1] = huobi.getMarketData();
		marketDataArray[2] = okcoin.getMarketData();
		//marketDataArray[3] = btcc.getMarketData();
	}
	
	public void updateMarketData(){
		huobi.updateMarketData();
		okcoin.updateMarketData();
		bitstamp.updateMarketData();
		//btcc.updateMarketData();
	}
	
	public MarketData[] getMarketDataArray() {
		return marketDataArray;
	}
	
	public huobiMarket getHuobiMarket(){
		return huobi;
	}
	
	public okcoinMarket getOkcoinMarket(){
		return okcoin;
	}
	
	public bitstampMarket getBitstampMarket(){
		return bitstamp;
	}
	
	public class huobiMarket {
		private static final String HUOBI_API_URL = "http://market.huobi.com/staticmarket/kline";
		//private static final String MINUTE = "001"+".html";
		//private static final String FIVE_MINUTE = "005"+".html";
		//private static final String FIFTEEN_MINUTE = "015"+".html";
		//private static final String THIRTY_MINUTE = "030"+".html";
		//private static final String HUOR = "060"+".html";
		private static final String DAY = "100"+".html";
		
		private MarketData huobiData;
		
		public huobiMarket(){
			this.huobiData = new MarketData("HUOBI","RMB","_ _ _","_ _ _","_ _ _");
		}
		
		public MarketData getMarketData(){
			return huobiData;
		}
		
		public void updateMarketData(){
			try{
				String s=HttpRequest.sendGet(HUOBI_API_URL + DAY, "");
				s = s.substring(s.length()-100);
		        String [] re = s.split(",");
		        huobiData.lastestPrice = re[re.length-3]; 
		        huobiData.minPrice = re[re.length-4];
		        huobiData.maxPrice = re[re.length-5];
			}catch (Exception e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		} 
	}
	
	public class okcoinMarket{
		private static final String OKCOIN_API_URL = "https://www.okcoin.com/api/ticker.do";
		private MarketData okcoinData;
		
		public okcoinMarket(){
			this.okcoinData = new MarketData("OKCOIN","RMB","_ _ _","_ _ _","_ _ _");
		}
		
		public MarketData getMarketData(){
			return okcoinData;
		}
		
		public void updateMarketData(){
			try{
				
				String s=HttpRequest.sendGet(OKCOIN_API_URL, "");
				JSONObject json;
			    try {
			    	json = new JSONObject(s);
			        String result = json.getString("ticker");
			        json = new JSONObject(result);
			        okcoinData.lastestPrice = json.getString("last");
			        okcoinData.maxPrice = json.getString("high");
			        okcoinData.minPrice = json.getString("low");
			        
			    } catch (JSONException e) {
			    	Log.e(TAG, e.toString());
			    	e.printStackTrace();
			    }
			    
			}catch (Exception e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}

		} 
	}
	
	public class bitstampMarket{
		private static final String BITSTAMP_API_URL = "http://www.bitstamp.net/api/ticker/";
		private MarketData bitstampData;
		
		public bitstampMarket(){
			this.bitstampData = new MarketData("BITSTAMP","USD","_ _ _","_ _ _","_ _ _");
		}
		
		public MarketData getMarketData(){
			return bitstampData;
		}
		
		public void updateMarketData(){
			try{
				
				String s=HttpRequest.sendGet(BITSTAMP_API_URL, "");
				JSONObject json;
			    try {
			    	json = new JSONObject(s);
			    	bitstampData.lastestPrice = json.getString("last");
			    	bitstampData.maxPrice = json.getString("high");
			    	bitstampData.minPrice = json.getString("low");
			        
			    } catch (JSONException e) {
			    	Log.e(TAG, e.toString());
			    	e.printStackTrace();
			    }
			    
			}catch (Exception e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
			
		} 
	}
	
	public class btcChinaMarket{
		private static final String BTCC_API_URL = "http://z.btc123.com/lib/TicInfo.js?type=btcchinaTicker";
		private MarketData btccData;
		
		public btcChinaMarket(){
			this.btccData = new MarketData("BTCCHINA","RMB","_ _ _","_ _ _","_ _ _");
		}
		
		public MarketData getMarketData(){
			return btccData;
		}
		
		public void updateMarketData(){
			try{
				
				String s=HttpRequest.sendGet(BTCC_API_URL, "");
				JSONObject json;
			    try {
			    	json = new JSONObject(s);
			        String result = json.getString("ticker");
			        json = new JSONObject(result);
			        btccData.lastestPrice = json.getString("last");
			        btccData.maxPrice = json.getString("high");
			        btccData.minPrice = json.getString("low");
			        
			    } catch (JSONException e) {
			    	Log.e(TAG, e.toString());
			    	e.printStackTrace();
			    }
			    
			}catch (Exception e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}

		} 
	}
	
	

}
