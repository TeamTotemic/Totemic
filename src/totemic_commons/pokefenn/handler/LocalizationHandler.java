package totemic_commons.pokefenn.handler;


import totemic_commons.pokefenn.lib.Localizations;
import totemic_commons.pokefenn.util.LocalizationUtil;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationHandler { 
	
	public static void loadLanguages(){
		
		
		 // For every file specified in the Localization library class, load them into the Language Registry
       for (String localizationFile : Localizations.localeFiles) {
           LanguageRegistry.instance().loadLocalization(localizationFile, LocalizationUtil.getLocaleFromFileName(localizationFile), LocalizationUtil.isXMLLanguageFile(localizationFile));
       }
	}
}





