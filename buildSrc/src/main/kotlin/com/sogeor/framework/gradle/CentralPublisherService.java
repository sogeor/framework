/*
 * Copyright 2025 Sogeor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sogeor.framework.gradle;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;

/**
 * @since 1.0.0-RC1
 */
public final class CentralPublisherService {

    /**
     * @since 1.0.0-RC1
     */
    public static final @NotNull String URL = "https://central.sonatype.com";

    /**
     * @since 1.0.0-RC1
     */
    private final @NotNull HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * @since 1.0.0-RC1
     */
    private final @NotNull String token;

    /**
     * @since 1.0.0-RC1
     */
    public CentralPublisherService(final @NotNull String username, final @NotNull String password) {
        this(Base64.getEncoder().encodeToString((username + ':' + password).getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * @since 1.0.0-RC1
     */
    public CentralPublisherService(final @NotNull String token) {
        this.token = token;
    }

    /**
     * @since 1.0.0-RC1
     */
    private @NotNull HttpRequest.Builder buildRequest(final @NotNull String url) {
        return HttpRequest.newBuilder(URI.create(URL + '/' + url)).header("Authorization", "Bearer " + token);
    }

    /**
     * @since 1.0.0-RC1
     */
    public void upload(final @NotNull Path path) {
        // TODO
    }

    /**
     * @since 1.0.0-RC1
     */
    public @NotNull HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * @since 1.0.0-RC1
     */
    public @NotNull String getToken() {
        return token;
    }

}
