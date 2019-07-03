package com.example.spring02.service.pdf;

import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements PdfService {

	@Inject
	CartService cartService;
	
	@Override
	public String createPdf() {
		String result = "";
		
		try {
			//pdf 문서 객체
			Document document = new Document();
			//pdf 생성
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/salmpe.pdf"));
			
			document.open();
			//한글 지원 폰트 지정
			BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\malgun.ttf", 
													BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//폰트 사이즈 지정
			Font font = new Font(baseFont, 12);
			
			//4컬럼 테이블
			PdfPTable table = new PdfPTable(4);
			//Chunk : 문단 (웹의 p태그와 비슷)
			Chunk chunk = new Chunk("장바구니", font);
			
			Paragraph ph = new Paragraph(chunk);
			ph.setAlignment(Element.ALIGN_CENTER);
			
			document.add(ph);
			document.add(Chunk.NEWLINE);	//줄바꿈
			document.add(Chunk.NEWLINE);
			//PdfPCell : 셀 생성(<td>)
			PdfPCell cell1 = new PdfPCell(new Phrase("상품명", font));
			PdfPCell cell2 = new PdfPCell(new Phrase("단가", font));
			PdfPCell cell3 = new PdfPCell(new Phrase("수량", font));
			PdfPCell cell4 = new PdfPCell(new Phrase("금액", font));
			
			//가운대 정렬
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			//태이블에 셀 추가
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			
			//장바구니 목록 리턴, listCart("사용자 아이디")
			List<CartDTO> items = cartService.listCart("kim");
			
			for(int i=0; i<items.size(); i++) {
				CartDTO dto = items.get(i);	//i번째 값을 dto에 저장
				
				PdfPCell cellProductName = new PdfPCell(new Phrase(dto.getProduct_name(), font)); 
				cellProductName.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellProductName);
				
				PdfPCell cellPrice = new PdfPCell(new Phrase(dto.getPrice()+"", font));
				cellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellPrice);
				
				PdfPCell cellAmount = new PdfPCell(new Phrase(dto.getAmount()+"", font));
				cellAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellAmount);
				
				PdfPCell cellMoney = new PdfPCell(new Phrase(dto.getMoney()+"", font));
				cellMoney.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellMoney);
				
			}
			
			//document에 table 추가
			document.add(table);
			//close 할 때 pdf 생성 
			document.close();
			
			result = "PDF 생성 완료!";
			
			
		} catch (Exception e) {
			result = "PDF 생성 실패...";
			e.printStackTrace();
		}
		
		return result;
	}

}
