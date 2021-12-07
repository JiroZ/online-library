package com.onlinelibrary.apigateway.configuration.filter

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class CorsFilter : WebFilter {
    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (ctx.request.headers.containsKey("Authorization")) {
            ctx.response.headers.add("Authorization", ctx.request.headers["Authorization"]?.get(0))
        }

        if (ctx != null) {
            //ctx.response.headers.add("Access-Control-Allow-Origin","*")
            ctx.response.headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, PATCH, PUT, OPTIONS")
            ctx.response.headers.add(
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin,Authorization,DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range"
            )
            return if (ctx.request.method == HttpMethod.OPTIONS) {
                ctx.response.headers.add("Access-Control-Allow-Origin", "*")
                ctx.response.headers.add("Access-Control-Max-Age", "1728000")
                ctx.response.statusCode = HttpStatus.NO_CONTENT
                Mono.empty()
            } else {
                ctx.response.headers.add(
                    "Access-Control-Expose-Headers",
                    "Access-Control-Allow-Origin,Authorization,Keep-Alive,User-Agent,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range"
                )
                chain?.filter(ctx) ?: Mono.empty()
            }
        } else {
            return chain?.filter(ctx) ?: Mono.empty()
        }
    }
}

