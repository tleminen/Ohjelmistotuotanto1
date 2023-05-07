package com.oh.time4play;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EmailServices {
    private String server;
    private int port;
    @NotNull
    public static final String CLIENT_NAME = "Android StackOverflow programmatic email";
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker)null);

    public final void send(@NotNull Email email) throws MessagingException {
        Intrinsics.checkNotNullParameter(email, "email");
        Properties props = new Properties();
        ((Map)props).put("mail.smtp.auth", "true");
        ((Map)props).put("mail.user", email.getFrom());
        ((Map)props).put("mail.smtp.host", this.server);
        ((Map)props).put("mail.smtp.port", this.port);
        ((Map)props).put("mail.smtp.starttls.enable", "true");
        ((Map)props).put("mail.smtp.ssl.trust", this.server);
        ((Map)props).put("mail.mime.charset", "UTF-8");
        Message msg = (Message)(new MimeMessage(Session.getDefaultInstance(props, email.getAuth())));
        msg.setFrom(email.getFrom());
        Calendar var10001 = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(var10001, "Calendar.getInstance()");
        msg.setSentDate(var10001.getTime());
        Message.RecipientType var15 = RecipientType.TO;
        Collection $this$toTypedArray$iv = (Collection)email.getToList();
        boolean $i$f$toTypedArray = false;
        Object[] var10002 = $this$toTypedArray$iv.toArray(new InternetAddress[0]);
        Intrinsics.checkNotNull(var10002, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        msg.setRecipients(var15, (Address[])var10002);
        msg.setReplyTo(new Address[]{email.getFrom()});
        msg.addHeader("X-Mailer", "Android StackOverflow programmatic email");
        msg.addHeader("Precedence", "bulk");
        msg.setSubject(email.getSubject());
        MimeMultipart var14 = new MimeMultipart();
        boolean var6 = false;
        MimeBodyPart var7 = new MimeBodyPart();
        boolean var10 = false;
        // oli "iso-8859-1" ja on edelleen
        var7.setText(email.getBody(), "iso-8859-1");
        Unit var11 = Unit.INSTANCE;
        var14.addBodyPart((BodyPart)var7);
        Unit var13 = Unit.INSTANCE;
        msg.setContent((Multipart)var14);
        Transport.send(msg);
    }

    public EmailServices(@NotNull String server, int port) {
        super();
        Intrinsics.checkNotNullParameter(server, "server");
        this.server = server;
        this.port = port;
    }

    public static final class Email {
        @NotNull
        private final Authenticator auth;
        @NotNull
        private final List toList;
        @NotNull
        private final Address from;
        @NotNull
        private final String subject;
        @NotNull
        private final String body;

        @NotNull
        public final Authenticator getAuth() {
            return this.auth;
        }

        @NotNull
        public final List getToList() {
            return this.toList;
        }

        @NotNull
        public final Address getFrom() {
            return this.from;
        }

        @NotNull
        public final String getSubject() {
            return this.subject;
        }

        @NotNull
        public final String getBody() {
            return this.body;
        }

        public Email(@NotNull Authenticator auth, @NotNull List toList, @NotNull Address from, @NotNull String subject, @NotNull String body) {
            super();
            Intrinsics.checkNotNullParameter(auth, "auth");
            Intrinsics.checkNotNullParameter(toList, "toList");
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(body, "body");
            this.auth = auth;
            this.toList = toList;
            this.from = from;
            this.subject = subject;
            this.body = body;
        }

        @NotNull
        public final Authenticator component1() {
            return this.auth;
        }

        @NotNull
        public final List component2() {
            return this.toList;
        }

        @NotNull
        public final Address component3() {
            return this.from;
        }

        @NotNull
        public final String component4() {
            return this.subject;
        }

        @NotNull
        public final String component5() {
            return this.body;
        }

        @NotNull
        public final Email copy(@NotNull Authenticator auth, @NotNull List toList, @NotNull Address from, @NotNull String subject, @NotNull String body) {
            Intrinsics.checkNotNullParameter(auth, "auth");
            Intrinsics.checkNotNullParameter(toList, "toList");
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(body, "body");
            return new Email(auth, toList, from, subject, body);
        }

        // $FF: synthetic method
        public static Email copy$default(Email var0, Authenticator var1, List var2, Address var3, String var4, String var5, int var6, Object var7) {
            if ((var6 & 1) != 0) {
                var1 = var0.auth;
            }

            if ((var6 & 2) != 0) {
                var2 = var0.toList;
            }

            if ((var6 & 4) != 0) {
                var3 = var0.from;
            }

            if ((var6 & 8) != 0) {
                var4 = var0.subject;
            }

            if ((var6 & 16) != 0) {
                var5 = var0.body;
            }

            return var0.copy(var1, var2, var3, var4, var5);
        }

        @NotNull
        public String toString() {
            return "Email(auth=" + this.auth + ", toList=" + this.toList + ", from=" + this.from + ", subject=" + this.subject + ", body=" + this.body + ")";
        }

        public int hashCode() {
            Authenticator var10000 = this.auth;
            int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
            List var10001 = this.toList;
            var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
            Address var2 = this.from;
            var1 = (var1 + (var2 != null ? var2.hashCode() : 0)) * 31;
            String var3 = this.subject;
            var1 = (var1 + (var3 != null ? var3.hashCode() : 0)) * 31;
            var3 = this.body;
            return var1 + (var3 != null ? var3.hashCode() : 0);
        }

        public boolean equals(@Nullable Object var1) {
            if (this != var1) {
                if (var1 instanceof Email) {
                    Email var2 = (Email)var1;
                    if (Intrinsics.areEqual(this.auth, var2.auth) && Intrinsics.areEqual(this.toList, var2.toList) && Intrinsics.areEqual(this.from, var2.from) && Intrinsics.areEqual(this.subject, var2.subject) && Intrinsics.areEqual(this.body, var2.body)) {
                        return true;
                    }
                }

                return false;
            } else {
                return true;
            }
        }
    }

    public static final class UserPassAuthenticator extends Authenticator {
        private final String username;
        private final String password;

        @NotNull
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.username, this.password);
        }

        public UserPassAuthenticator(@NotNull String username, @NotNull String password) {
            super();
            Intrinsics.checkNotNullParameter(username, "username");
            Intrinsics.checkNotNullParameter(password, "password");
            this.username = username;
            this.password = password;
        }
    }

    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

