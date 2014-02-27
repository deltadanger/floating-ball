package helper;

import java.util.HashMap;
import java.util.Locale;

public class Translate {

    public static final String TITLE = "floating_ball";
    public static final String APP_URL = "app_url";
    public static final String SPEED = "speed";
    public static final String GAME= "game";
    public static final String OVER= "over";
    public static final String SCORE = "score";
    public static final String HIGH_SCORE = "high_score:";
    public static final String STATUS_UPDATE = "status_update";
    public static final String STATUS_UPDATE_SUCCESS = "status_update_success";
    public static final String STATUS_UPDATE_FAILURE = "status_update_failure";
    public static final String STATUS_UPDATE_CONFIRM_TITLE = "status_update_confirm_title";
    public static final String STATUS_UPDATE_CONFIRM= "status_update_confirm";
    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String SCORE_PLACEHOLDER = "{{score}}";
    public static final String STATUS_PLACEHOLDER = "{{status}}";
    public static final String SOCIAL_NETWORK_PLACEHOLDER = "{{social_network}}";
    
    private static final String UNDEFINED = "[[undefined]]";
    
    public static String t(String token) {
        HashMap<String, String> map;
        if (data.containsKey(Locale.getDefault().getLanguage())) {
            map = data.get(Locale.getDefault().getLanguage());
        } else {
            map = data.get("en");
        }
        
        if (map.containsKey(token)) {
            return map.get(token);
        }
        
        return UNDEFINED;
    }
    
    @SuppressWarnings("serial")
    private static final HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String,String>>() {{
        
        this.put("en", new HashMap<String, String>(){{
            this.put(TITLE, "Floating Ball");
            this.put(APP_URL, "http://onelink.to/ud59hg");
            this.put(SPEED, "Speed");
            this.put(GAME, "Game");
            this.put(OVER, "Over");
            this.put(SCORE, "Score:");
            this.put(HIGH_SCORE, "High Score:");
            this.put(STATUS_UPDATE, "I just scored " + SCORE_PLACEHOLDER + " at Floating Ball! Can someone do better than that?");
            this.put(STATUS_UPDATE_SUCCESS, "Status updated!");
            this.put(STATUS_UPDATE_FAILURE, "Error while updating status, please try again later.");
            this.put(STATUS_UPDATE_CONFIRM_TITLE, "Publishing new score");
            this.put(STATUS_UPDATE_CONFIRM, "The following message will be posted on " + SOCIAL_NETWORK_PLACEHOLDER + ":\n\"" + STATUS_PLACEHOLDER + "\"\nDo you want to continue?");
            this.put(YES, "Yes");
            this.put(NO, "No");
        }});
        
        this.put("fr", new HashMap<String, String>(){{
            this.put(TITLE, "Floating Ball");
            this.put(APP_URL, "http://onelink.to/ud59hg");
            this.put(SPEED, "Vitesse");
            this.put(GAME, "Game");
            this.put(OVER, "Over");
            this.put(SCORE, "Score:");
            this.put(HIGH_SCORE, "Meilleur Score:");
            this.put(STATUS_UPDATE, "Je viens de faire " + SCORE_PLACEHOLDER + " a Floating Ball ! Quelqu'un peut faire mieux que ça ?");
            this.put(STATUS_UPDATE_SUCCESS, "Statut mis à jour!");
            this.put(STATUS_UPDATE_FAILURE, "Problème lors de la mise à jour du statut, veuillez réessayer plus tard.");
            this.put(STATUS_UPDATE_CONFIRM_TITLE, "Publication du score");
            this.put(STATUS_UPDATE_CONFIRM, "Le message suivant va être posté sur " + SOCIAL_NETWORK_PLACEHOLDER + " :\n\"" + STATUS_PLACEHOLDER + "\"\nVoulez-vous continuer ?");
            this.put(YES, "Oui");
            this.put(NO, "Non");
        }});
    }};
}
