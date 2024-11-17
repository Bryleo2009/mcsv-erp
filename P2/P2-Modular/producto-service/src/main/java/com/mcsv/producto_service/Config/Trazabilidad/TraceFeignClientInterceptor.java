package com.mcsv.producto_service.Config.Trazabilidad;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;

@Component
public class TraceFeignClientInterceptor implements RequestInterceptor {

    private final Tracer tracer;

    public TraceFeignClientInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (tracer != null && tracer.currentSpan() != null) {
            Span span = tracer.currentSpan();
            assert span != null;
            String traceId = span.context().traceId();
            String spanId = span.context().spanId();
            String parentSpanId = span.context().parentId() != null
                    ? span.context().parentId()
                    : null; // Omitir si es null

            // Propagar los encabezados solo si son válidos
            requestTemplate.header("X-B3-TraceId", traceId);
            requestTemplate.header("X-B3-SpanId", spanId);

            if (parentSpanId != null) {
                requestTemplate.header("X-B3-ParentSpanId", parentSpanId);
            }

            requestTemplate.header("X-B3-Sampled", span.context().sampled() ? "1" : "0");
        }

    }
}
