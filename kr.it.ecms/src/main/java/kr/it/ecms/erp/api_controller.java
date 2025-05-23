package kr.it.ecms.erp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class api_controller {
		
		PrintWriter pw = null;
		
		
		//JQ - Ajax (POST) - FormData
		@PostMapping("/ajax/api_ajax6.do")
		public String ajax6(ServletResponse res,
				@RequestParam(name="fdata")String data) throws Exception {
			this.pw = res.getWriter();
			System.out.println(data);
			
			this.pw.print("ok");
			this.pw.close();
			return null;
		}
		
		
		//JQ - Ajax (POST) - JSON.stringify
		@PostMapping("/ajax/api_ajax5.do")
		public String ajax5(ServletResponse res,
			@RequestBody String all_data) throws Exception {
			
			this.pw = res.getWriter();
			System.out.println(all_data);
			JSONObject jo = new JSONObject(all_data);
			System.out.println(jo.get("fdata"));
			
			JSONArray ja = (JSONArray)jo.get("fdata");
			System.out.println(ja.get(0));
			
			this.pw.print("ok");
			this.pw.close();
			return null;
		}
		
		
		
		//JQ - Ajax (GET)
		@GetMapping("/ajax/api_ajax4.do")
		public String ajax4(ServletResponse res,
				@RequestParam(name="no")String no
				) throws Exception {
			this.pw = res.getWriter();
			System.out.println(no);
			JSONArray ja = new JSONArray(no);
			System.out.println(ja.get(0));
			
			this.pw.print("ok");
			this.pw.close();
			return null;
		}
				
		//JS-Ajax(POST + FormData)
		@PostMapping("/ajax/api_ajax3.do")
		public String ajax3(
				ServletResponse res,
				@RequestHeader("indata")String keycode,
				@RequestParam(name="product")String product)throws Exception {
			System.out.println(keycode);
			this.pw = res.getWriter();
			if(keycode.equals("apink")) {
				System.out.println(product);
				String product_name[] = product.split(",");
				System.out.println(product_name[0]);
				this.pw.print("ok");
			}
			else {
				this.pw.print("error");
			}
			this.pw.close();
			return null;
		}
		
		
		
		//JS-Ajax(POST + JSON.stringify)
		@PostMapping("/ajax/api_ajax2.do")
		public String ajax2(@RequestBody String aaa, ServletResponse res)throws Exception {
			this.pw = res.getWriter();
			try {
				JSONObject jo = new JSONObject(aaa);
				int ea = jo.length();
				int w = 0;
				while(w < ea) {
					System.out.println(jo.get(String.valueOf(w)));
					w++;
				}
				
			}catch (Exception e) {
			
			}
			this.pw.print("ok");
			this.pw.close();
			return null;
		}
		
		
	
		//JS-Ajax(POST)
		@PostMapping("/ajax/api_ajax1.do")
		public String ajax1(
				@RequestParam(name="product") String product,
				ServletResponse res) throws Exception {
			this.pw = res.getWriter();
			System.out.println(product);
			
			this.pw.print("ok");
			this.pw.close();
			return null;
		}
	
}
