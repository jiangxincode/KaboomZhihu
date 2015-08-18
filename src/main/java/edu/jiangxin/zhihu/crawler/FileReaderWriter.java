package edu.jiangxin.zhihu.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * �����ļ��Ķ�д������װ�� 
 */
public class FileReaderWriter {
	/** 
	 * @param filePath 
	 *            �ļ�·�����ַ�����ʾ��ʽ 
	 * @param keyWord 
	 *            ���Ұ���ĳ���ؼ��ֵ���Ϣ����nullΪ���ؼ��ֲ�ѯ��nullΪȫ����ʾ 
	 * @return ���ļ�����ʱ�������ַ��������ļ�������ʱ������null 
	 */
	public static String ReadFromFile(String filePath, String keyWord) {
		StringBuffer stringBuffer = null;
		File file = new File(filePath);
		
		if (file.exists()) {
			stringBuffer = new StringBuffer();
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			String temp = "";
			try {
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				while ((temp = bufferedReader.readLine()) != null) {
					if (keyWord == null) {
						stringBuffer.append(temp + "\n");
					} else {
						if (temp.contains(keyWord)) {
							stringBuffer.append(temp + "\n");
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fileReader != null)
						fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (bufferedReader != null)
						bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (stringBuffer == null) {
			return null;
		} else {
			return stringBuffer.toString();
		}

	}

	/** 
	 * ��ָ���ַ���д���ļ�������������ļ�·�������ڣ����½��ļ���д�롣 
	 *  
	 * @param content 
	 *            Ҫд���ļ������� 
	 * @param filePath 
	 *            �ļ�·�����ַ�����ʾ��ʽ��Ŀ¼�Ĳ�ηָ������ǡ�/��Ҳ�����ǡ�\\�� 
	 * @param isAppend 
	 *            true��׷�ӵ��ļ���ĩβ��false���Ը���ԭ�ļ��ķ�ʽд�� 
	 */
	public static boolean writeIntoFile(String content, String filePath, boolean isAppend) {
		boolean isSuccess = true;
		
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				File parent = file.getParentFile();
				if(!parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// д���ļ�
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, isAppend);
			fileWriter.write(content);
			fileWriter.flush();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return isSuccess;
	}

	/** 
	 * ��ȡ��ǰʱ�䣬�����ļ����� 
	 *  
	 * @param format 
	 *            yyyy ��ʾ4λ�꣬ MM ��ʾ2λ�£� dd ��ʾ2λ�գ�hhСʱ��mm���ӡ� 
	 *  
	 * @return true:�����ɹ���false�������ɹ� 
	 */
	public static String getNowTime(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}

	/** 
	 * �����ļ���������ļ��Ѵ��ڽ����ٴ������������κ����ã� 
	 *  
	 * @param filePath 
	 *            Ҫ�����ļ���·�����ַ�����ʾ��ʽ��Ŀ¼�Ĳ�ηָ������ǡ�/��Ҳ�����ǡ�\\�� 
	 *  
	 * @return true:�����ɹ���false�������ɹ� 
	 */
	public static boolean createNewFile(String filePath) {
		boolean isSuccess = true;
		// ������"\\"תΪ"/",û���򲻲����κα仯
		String filePathTurn = filePath.replaceAll("\\\\", "/");
		// �ȹ��˵��ļ���
		int index = filePathTurn.lastIndexOf("/");
		String dir = filePathTurn.substring(0, index);
		// �ٴ����ļ���
		File fileDir = new File(dir);
		isSuccess = fileDir.mkdirs();
		// �����ļ�
		File file = new File(filePathTurn);
		try {
			isSuccess = file.createNewFile();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}
}