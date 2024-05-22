package com.neatflix.api;

import androidx.annotation.NonNull;

import com.apollographql.apollo3.api.ApolloRequest;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.api.Operation;
import com.apollographql.apollo3.interceptor.ApolloInterceptor;
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain;

import kotlinx.coroutines.flow.Flow;

public class GraphQLInspector implements ApolloInterceptor {
    @NonNull
    @Override
    public <D extends Operation.Data> Flow<ApolloResponse<D>> intercept(@NonNull ApolloRequest<D> apolloRequest, @NonNull ApolloInterceptorChain apolloInterceptorChain) {
        return null;
    }
}
