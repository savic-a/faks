package iss.spring.web.entities.lob;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class ClientInfo {

	private String address;

	@Lob
	@Column(length = 65535)
	@JsonIgnore
	private ImageIcon photo;

	@Lob
	@Column(length = 65536)
	@JsonIgnore
	private byte[] imageData;
	
	private String mimeType;
	
	private int w;
	
	private int h;
	
	public ClientInfo() {
		
	}

	public ClientInfo(String address, String imgPath) {
		this();
		this.address = address;
		
		byte[] imgData;
		try {
			File f = new File(imgPath);
			FileInputStream in = new FileInputStream(f);
			int len = (int) f.length();
			imgData = new byte[len];
			in.read(imgData);
			in.close();

			setImageData(imgData);
			setPhoto(new ImageIcon(imgData));
			
			this.w = this.photo.getIconWidth();
			this.h = this.photo.getIconHeight();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
			String mimeType = URLConnection.guessContentTypeFromStream(bais);
			bais.close();
			setMimeType(mimeType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public ImageIcon getPhoto() {
		return photo;
	}


	public void setPhoto(ImageIcon photo) {
		this.photo = photo;
	}


	public byte[] getImageData() {
		return imageData;
	}


	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	@Override
	public String toString() {
		return "ClientInfo [address=" + address + ", mimeType=" + mimeType + ", w=" + w + ", h="
				+ h + "]";
	}

}
