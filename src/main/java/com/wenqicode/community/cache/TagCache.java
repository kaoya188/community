package com.wenqicode.community.cache;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.wenqicode.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用于缓存标签Tag
 *
 * @author Wenqi Liang
 * @date 2020/10/20
 */
public class TagCache {

    public static List<TagDTO> get() {

        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("后端");
        program.setTags(Arrays.asList("php", "python", "c++", "golang", "c", "spring", "后端", "springboot", "django",
                "flask", "c#", "swoole", "ruby", "asp.net", "ruby-on-rails"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("前端");
        framework.setTags(Arrays.asList("javascript", "前端", "vue.js", "css", "html", "node.js", "react.js", "css3",
                "es6", "typescript"));
        tagDTOS.add(framework);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql", "redis", "sql", "mongodb", "数据库", "json", "elasticsearch", "nosql",
                "memcached", "postgresql", "sqlite", "mariadb"));
        tagDTOS.add(database);

        TagDTO mobile = new TagDTO();
        mobile.setCategoryName("移动端");
        mobile.setTags(Arrays.asList("java", "android", "ios", "objective-c", "小程序", "react-native", "swift", "xcode"
                , "android-studio", "flutter", "webapp", "kotlin"));
        tagDTOS.add(mobile);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "macos", "visual-studio-code", "windows", "vim", "sublime-text", "intellij-idea", "phpstorm", "eclipse", "webstorm", "编辑器", "svn", "visual-studio", "pycharm", "emacs"));
        tagDTOS.add(tool);

        return tagDTOS;
    }


    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(tag -> !tagList.contains(tag)).collect(Collectors.joining(","));
        return invalid;
    }
}
