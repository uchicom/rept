package com.uchciom.rept;

import java.awt.Color;
import java.awt.Font;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.yaml.snakeyaml.Yaml;

import com.uchciom.rept.dto.Draw;
import com.uchciom.rept.dto.Line;
import com.uchciom.rept.dto.Template;
import com.uchciom.rept.dto.Text;
import com.uchciom.rept.dto.Value;

public class Yamlpdf implements Closeable {

	/**
	 * ページは、自分で設定する。 PDPageを生成するメソッド Map<String, Object> param, document,
	 * Templateを渡す
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try (PDDocument document = new PDDocument()) {
			Yaml yaml = new Yaml();
			System.out.println((System.currentTimeMillis() - start) + "[msec]yaml create");
			start = System.currentTimeMillis();
			Template template = yaml.loadAs(
					new String(Files.readAllBytes(new File("src/test/resources/template.yaml").toPath())),
					Template.class);
//			System.out.println(template);

			System.out.println((System.currentTimeMillis() - start) + "[msec]template create");
			start = System.currentTimeMillis();
			// PDFドキュメントを作成

			// DocumentへのObjectの登録はContentStream生成の前で実施。
			// サイズ指定
			// ページを追加(1ページ目)
			Yamlpdf yamlPdf = new Yamlpdf(document, template);

			System.out.println((System.currentTimeMillis() - start) + "[msec]yamlPdf create");
			start = System.currentTimeMillis();
			Map<String, Object> paramMap = new HashMap<>();

			for (int i = 0; i < 10; i++) {
				System.out.println(i);
				paramMap.put("name", i + "株式会社");
				yamlPdf.addPage(paramMap);
				File outFile = new File("result/" + i + "test.pdf");
				outFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(outFile);
				document.save(fos);
				fos.close();
			}
			document.save("test.pdf");
			System.out.println((System.currentTimeMillis() - start) + "[msec]yamlPdf page create");
			yamlPdf.close();
			// 作成したPDFを保存
			System.out.println((System.currentTimeMillis() - start) + "[msec]pdf save");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Map<String, PDImageXObject> xImageMap = new HashMap<>();
	PDDocument document;
	Template template;
	PDFont font;
	TrueTypeCollection ttc;

	public Yamlpdf(PDDocument document, Template template) throws IOException {
		long start = System.currentTimeMillis();
		ttc = new TrueTypeCollection(Files.newInputStream(Paths.get("C:/Windows/Fonts/msgothic.ttc")));// TODO
		System.out.println((System.currentTimeMillis() - start) + "[msec]ttc create");
		start = System.currentTimeMillis();
		TrueTypeFont ttf = ttc.getFontByName("MS-Gothic");
		System.out.println((System.currentTimeMillis() - start) + "[msec]ttf create");
		start = System.currentTimeMillis();
		font = PDType0Font.load(document, ttf, true);
		System.out.println((System.currentTimeMillis() - start) + "[msec]document font load");
		start = System.currentTimeMillis();
		Map<String, URL> imageMap = template.getSpec().getImageMap();
		imageMap.forEach((key, value) -> {
			try {
				xImageMap.put(key, PDImageXObject.createFromFile("sample.png", document));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		//addPageする前に初期状態を準備しておいて、出力内容を使いまわして保存する。
		System.out.println((System.currentTimeMillis() - start) + "[msec]imageMap create");
		start = System.currentTimeMillis();
		this.document = document;
		this.template = template;
	}

	public void addPage(Map<String, Object> paramMap) throws IOException {
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		Map<String, Color> colorMap = template.getSpec().getColorMap();
		Map<String, Line> lineMap = template.getSpec().getLineMap();
		Map<String, Text> textMap = template.getSpec().getTextMap();
		Map<String, Font> fontMap = template.getSpec().getFontMap();

		// 書き込む用のストリームを準備
		PDPageContentStream stream = new PDPageContentStream(document, page);
		for (Draw draw : template.getDraws()) {
			switch (draw.getType()) {
			case "line":// OK
				Line line = lineMap.get(draw.getKey());
				Color color = colorMap.get(line.getColorKey());
				int lineWidth = line.getWidth();
				for (Value value : draw.getValues()) {

					stream.setLineWidth(lineWidth);
					stream.setStrokingColor(color);
					stream.moveTo(value.getX1(), value.getY1());
					stream.lineTo(value.getX2(), value.getY2());
					stream.stroke();
				}
				break;
			case "rectangle": // これでOK
				Line line1 = lineMap.get(draw.getKey());
				Color color1 = colorMap.get(line1.getColorKey());
				int lineWidth1 = line1.getWidth();
				for (Value value : draw.getValues()) {
					// 塗りつぶしかどうか
					stream.setLineWidth(lineWidth1);
					stream.setStrokingColor(color1);
					stream.addRect(value.getX1(), value.getY1(), value.getX2() - value.getX1(),
							value.getY2() - value.getY1());
					if (value.isFill()) {
						stream.fill();// 塗りつぶし
					} else {
						stream.stroke();
					}
				}
				break;
			case "string":
				Text text = textMap.get(draw.getKey());
				Color color2 = colorMap.get(text.getColorKey());

				Font font2 = fontMap.get(text.getFontKey());
				stream.setStrokingColor(color2);
				stream.setFont(font, font2.getSize());

				for (Value value : draw.getValues()) {
					stream.beginText();
					stream.newLineAtOffset(value.getX1(), value.getY1());
					if (value.getValue().contains("${name}")) {
						stream.showText(value.getValue().replace("${name}", paramMap.get("name").toString()));
					} else {
						stream.showText(value.getValue());
					}
					stream.endText();
				}
				break;
			case "image":
				// イメージ描画（今回は使い回し）
				PDImageXObject imagex = xImageMap.get(draw.getKey());
				for (Value value : draw.getValues()) {
					if (value.getX1() == value.getX2()) {
						stream.drawImage(imagex, value.getX1(), value.getY1());
					} else {
						stream.drawImage(imagex, value.getX1(), value.getY1(), value.getX2() - value.getX1(),
								value.getY2() - value.getY1());
					}
				}
				break;
			default:
				break;
			}

		}
		// ストリームを閉じる
		stream.close();
	}

	@Override
	public void close() throws IOException {

		ttc.close();
	}
}