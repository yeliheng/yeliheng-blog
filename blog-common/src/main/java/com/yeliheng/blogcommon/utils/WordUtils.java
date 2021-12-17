package com.yeliheng.blogcommon.utils;

/**
 * 文章单词相关统计工具
 */
public class WordUtils {

    public static final int readingWordsPerMin = 250;

    /**
     * 统计文章字数
     * @param string 文章字符串
     * @return 字数
     */
    public static int wordCount(String string) {
        if (string == null) {
            return 0;
        }
        String englishString = string.replaceAll("[\u4e00-\u9fa5]", "");
        String[] englishWords = englishString.split("[\\p{P}\\p{S}\\p{Z}\\s]+");
        int chineseWordCount = string.length() - englishString.length();
        int otherWordCount = englishWords.length;
        if (englishWords.length > 0 && englishWords[0].length() < 1) {
            otherWordCount--;
        }
        if (englishWords.length > 1 && englishWords[englishWords.length - 1].length() < 1) {
            otherWordCount--;
        }
        return chineseWordCount + otherWordCount;
    }

    /**
     * 统计文章阅读大约需要多少时间（分钟）
     * @param wordsCount 文章字数
     * @return 阅读所需的时间长度
     */
    public static int calReadingTimeByWords(int wordsCount) {
        int time = Math.round( (float) wordsCount / readingWordsPerMin);
        if(time > 0){
            return time;
        }else {
            return 1;
        }
    }
}
