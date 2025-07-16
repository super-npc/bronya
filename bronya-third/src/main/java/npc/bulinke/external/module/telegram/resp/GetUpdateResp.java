package npc.bulinke.external.module.telegram.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该对象表示传入的更新，比如接收到用户发来的新消息，就会获得新的更新
 * 任何给定的更新中最多只能存在一个可选参数
 */
@Data
@NoArgsConstructor
public class GetUpdateResp {
    /**
     * 更新的唯一标识符。更新标识符从某个正数开始，并依次增加。
     * 如果您使用的是Webhooks，则此ID变得尤为方便，因为它使您可以忽略重复的更新或在错误的情况下恢复正确的更新顺序。
     * 如果至少有一个星期没有新更新，则将随机选择下一个更新的标识符，而不是顺序选择。
     */
    private Long updateId;
    
    /**
     * 可选的。任何形式的新传入消息-文本，照片，贴纸等
     */
    private Message message;
    
    /**
     * 可选的。机器人成员状态更新的信息
     */
    private MyChatMember myChatMember;
    
    /**
     * 该对象代表一个消息
     */
    @Data
    @NoArgsConstructor
    public static class Message {
        /**
         * 此聊天中的唯一消息标识符
         */
        private Long messageId;
        
        /**
         * 可选的。发件人，对于发送到channels的消息为空
         */
        private From from;
        
        /**
         * 消息所属的会话
         */
        private Chat chat;
        
        /**
         * 发送时间（Unix时间）
         */
        private Long date;
        
        /**
         * 可选的。对于文本消息，消息的实际UTF-8文本，0-4096个字符
         */
        private String text;
        
        /**
         * 可选的。添加到组或超组中的新成员信息
         */
        private NewChatParticipant newChatParticipant;
        
        /**
         * 可选的。添加到组或超组中的新成员信息
         */
        private NewChatMember newChatMember;
        
        /**
         * 可选的。添加到组或超组中的新成员信息数组
         */
        private NewChatMember[] newChatMembers;
        
        /**
         * 可选的。对于文本消息，出现在文本中的特殊实体，例如用户名，URL，机器人命令等
         */
        private MessageEntity[] entities;
    }
    
    /**
     * 该对象表示机器人成员状态更新的信息
     */
    @Data
    @NoArgsConstructor
    public static class MyChatMember {
        /**
         * 消息所属的会话
         */
        private Chat chat;
        
        /**
         * 执行更新的用户
         */
        private From from;
        
        /**
         * 更新发生的时间（Unix时间）
         */
        private Long date;
        
        /**
         * 更新前的成员状态
         */
        private ChatMember oldChatMember;
        
        /**
         * 更新后的成员状态
         */
        private ChatMember newChatMember;
    }
    
    /**
     * 该对象表示telegram的一个用户或者机器人
     */
    @Data
    @NoArgsConstructor
    public static class From {
        /**
         * 该用户或机器人的唯一标识
         */
        private Long id;
        
        /**
         * 标识该用户是否是机器人，True如果是机器人
         */
        private Boolean isBot;
        
        /**
         * 用户或者机器人的first_name
         */
        private String firstName;
        
        /**
         * 可选。用户或者机器人的last_name
         */
        private String lastName;
        
        /**
         * 可选。用户或者机器人的username
         */
        private String username;
        
        /**
         * 可选。用户语言的IETF语言标签
         */
        private String languageCode;
    }
    
    /**
     * 该对象表示一个聊天信息
     */
    @Data
    @NoArgsConstructor
    public static class Chat {
        /**
         * 该聊天的唯一标识符。这个数字可能会大于32位但是一定小于52位所以编程时因指定int64类型
         */
        private Long id;
        
        /**
         * 可选。标题, 针对 supergroups, channels 和 group 类型的聊天
         */
        private String title;
        
        /**
         * 聊天的类型，可以是 "private", "group", "supergroup" 或者 "channel"
         */
        private String type;
        
        /**
         * 可选。私人聊天中对方的first_name
         */
        private String firstName;
        
        /**
         * 可选。私人聊天中对方的last_name
         */
        private String lastName;
        
        /**
         * 可选。Username, 针对 私有的聊天，如果可以的话也针对 supergroups 和 channels
         */
        private String username;
    }
    
    /**
     * 该对象表示聊天成员的信息
     */
    @Data
    @NoArgsConstructor
    public static class ChatMember {
        /**
         * 成员的用户信息
         */
        private User user;
        
        /**
         * 成员在聊天中的状态
         */
        private String status;
        
        /**
         * 是否可以编辑该成员
         */
        private Boolean canBeEdited;
        
        /**
         * 是否可以管理聊天
         */
        private Boolean canManageChat;
        
        /**
         * 是否可以更改聊天信息
         */
        private Boolean canChangeInfo;
        
        /**
         * 是否可以删除消息
         */
        private Boolean canDeleteMessages;
        
        /**
         * 是否可以邀请用户
         */
        private Boolean canInviteUsers;
        
        /**
         * 是否可以限制成员
         */
        private Boolean canRestrictMembers;
        
        /**
         * 是否可以置顶消息
         */
        private Boolean canPinMessages;
        
        /**
         * 是否可以管理话题
         */
        private Boolean canManageTopics;
        
        /**
         * 是否可以提升成员
         */
        private Boolean canPromoteMembers;
        
        /**
         * 是否可以管理视频聊天
         */
        private Boolean canManageVideoChats;
        
        /**
         * 是否可以发布故事
         */
        private Boolean canPostStories;
        
        /**
         * 是否可以编辑故事
         */
        private Boolean canEditStories;
        
        /**
         * 是否可以删除故事
         */
        private Boolean canDeleteStories;
        
        /**
         * 是否是匿名管理员
         */
        private Boolean isAnonymous;
        
        /**
         * 是否可以管理语音聊天
         */
        private Boolean canManageVoiceChats;
    }
    
    /**
     * 该对象表示一个用户的基本信息
     */
    @Data
    @NoArgsConstructor
    public static class User {
        /**
         * 该用户或机器人的唯一标识
         */
        private Long id;
        
        /**
         * 标识该用户是否是机器人，True如果是机器人
         */
        private Boolean isBot;
        
        /**
         * 用户或者机器人的first_name
         */
        private String firstName;
        
        /**
         * 可选。用户或者机器人的username
         */
        private String username;
    }
    
    /**
     * 该对象表示新加入聊天的成员信息
     */
    @Data
    @NoArgsConstructor
    public static class NewChatParticipant {
        /**
         * 该用户或机器人的唯一标识
         */
        private Long id;
        
        /**
         * 标识该用户是否是机器人，True如果是机器人
         */
        private Boolean isBot;
        
        /**
         * 用户或者机器人的first_name
         */
        private String firstName;
        
        /**
         * 可选。用户或者机器人的username
         */
        private String username;
    }
    
    /**
     * 该对象表示新加入聊天的成员信息
     */
    @Data
    @NoArgsConstructor
    public static class NewChatMember {
        /**
         * 该用户或机器人的唯一标识
         */
        private Long id;
        
        /**
         * 标识该用户是否是机器人，True如果是机器人
         */
        private Boolean isBot;
        
        /**
         * 用户或者机器人的first_name
         */
        private String firstName;
        
        /**
         * 可选。用户或者机器人的username
         */
        private String username;
    }
    
    /**
     * 该对象表示文本消息中的一个特殊实体。例如，标签，用户名，URL等
     */
    @Data
    @NoArgsConstructor
    public static class MessageEntity {
        /**
         * 以UTF-16代码单位向实体开始的偏移量
         */
        private Integer offset;
        
        /**
         * 实体的长度（以UTF-16代码单元为单位）
         */
        private Integer length;
        
        /**
         * 实体的类型。可以是"mention"（@username），"hashtag"（#hashtag），"cashtag"（$ USD），
         * "bot_command"（/ start @ jobs_bot），"URL"（https://telegram.org），
         * "email"（do-not-reply@telegram.org），"phone_number"（+ 1-212-555-0123），
         * "bold"（粗体），"italic"（斜体），"underline"（带下划线的文本），
         * "strikethrough"（删除线文本），"code"（等宽字符串），"pre"（等宽块），
         * "text_link"（对于可点击的文本URL），"text_mention"（对于没有用户名的用户）
         */
        private String type;
    }
} 