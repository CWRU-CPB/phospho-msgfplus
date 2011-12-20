package params;

import java.io.File;
import java.util.ArrayList;

import msutil.FileFormat;

public class FileListParameter extends Parameter {

	private ArrayList<FileFormat> fileFormats = new ArrayList<FileFormat>();
	
	private File[] files;
	private FileFormat[] fileFormatArr;
	
	public FileListParameter(String key, String name, String description) {
		super(key, name, description);
	}

	public FileListParameter setAsOptional()
	{
		super.setOptional();
		return this;
	}
	
	public FileListParameter addFileFormat(FileFormat fileFormat)
	{
		fileFormats.add(fileFormat);
		return this;
	}
	
	@Override
	public String parse(String value) 
	{
		File path = new File(value);
		
		if(!path.isDirectory())
			return "must be a directory";
		
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<FileFormat> fileFormatList = new ArrayList<FileFormat>();
		for(File f : path.listFiles())
		{
			if(fileFormats.isEmpty())
			{
				fileList.add(f);
			}
			else
			{
				FileFormat matchedFormat = null;
				String fileName = f.getName();
				
				for(FileFormat format : fileFormats)
				{
					if(!format.isCaseSensitive())
						fileName = fileName.toLowerCase();
					for(String suffix : format.getSuffixes())
					{
						if(!format.isCaseSensitive())
							suffix = suffix.toLowerCase();
						if(fileName.endsWith(suffix))
						{
							matchedFormat = format;
							break;
						}
					}
				}
				if(matchedFormat != null)
				{
					fileList.add(f);
					fileFormatList.add(matchedFormat);
				}
			}
		}
		if(fileList.size() == 0)
		{
			return "file does not exist";
		}
		
		files = fileList.toArray(new File[0]);
		fileFormatArr = fileFormatList.toArray(new FileFormat[0]);
		return null;
	}
	
	public File[] getFiles()
	{
		return files;
	}
	
	public FileFormat[] getFileFormats()
	{
		return fileFormatArr;
	}
	
	@Override
	public String getValueAsString() {
		if(files == null)
			return null;
		StringBuffer output = new StringBuffer();
		if(files.length == 0)
			return output.toString();
		output.append(files[0].getPath());
		for(int i=1; i<files.length; i++)
			output.append(","+files[i].getPath());
		return output.toString();
	}
}