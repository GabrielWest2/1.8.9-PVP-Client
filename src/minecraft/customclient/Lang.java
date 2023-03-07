package customclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class Lang {
	
    private static final Splitter splitter = Splitter.on('=').limit(2);
    private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	
    public static void load() {
    	Map map = I18n.getLocaleProperties();
    
    	InputStream fIn;
    	
		try {
			fIn = getResourceStream(new ResourceLocation("customclient/lang/en_US.lang"));
			loadLocaleData(fIn, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private static InputStream getResourceStream(ResourceLocation location)
    {
        String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
        InputStream inputstream = Lang.class.getResourceAsStream(s);
        return inputstream != null ? inputstream : DefaultResourcePack.class.getResourceAsStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());
    }
    
	public static void loadLocaleData(InputStream fileIn, Map mappings) throws IOException
    {
        for (String s : IOUtils.readLines(fileIn, Charsets.UTF_8))
        {
            if (!s.isEmpty() && s.charAt(0) != 35)
            {
                String[] astring = (String[])((String[])Iterables.toArray(splitter.split(s), String.class));

                if (astring != null && astring.length == 2)
                {
                    String s1 = astring[0];
                    String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
                    mappings.put(s1, s2);
                }
            }
        }
    }
}
