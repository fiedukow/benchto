/*
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
package com.teradata.benchto.driver.listeners;

import com.teradata.benchto.driver.Benchmark;
import com.teradata.benchto.driver.execution.BenchmarkExecutionResult;
import com.teradata.benchto.driver.execution.QueryExecution;
import com.teradata.benchto.driver.execution.QueryExecutionResult;
import com.teradata.benchto.driver.listeners.benchmark.BenchmarkExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingBenchmarkExecutionListener
        implements BenchmarkExecutionListener
{

    private static final Logger LOG = LoggerFactory.getLogger(LoggingBenchmarkExecutionListener.class);

    @Override
    public void benchmarkStarted(Benchmark benchmark)
    {
        LOG.info("Executing benchmark: {}", benchmark.getName());
    }

    @Override
    public void benchmarkFinished(BenchmarkExecutionResult result)
    {
        LOG.info("Finished benchmark: {}", result.getBenchmark().getName());
    }

    @Override
    public void executionStarted(QueryExecution execution)
    {
        LOG.info("Query started: {} ({}/{})", execution.getQueryName(), execution.getRun(), execution.getBenchmark().getRuns());
    }

    @Override
    public void executionFinished(QueryExecutionResult result)
    {
        if (result.isSuccessful()) {
            LOG.info("Query finished: {} ({}/{}), rows count: {}, duration: {}", result.getQueryName(), result.getQueryExecution().getRun(),
                    result.getBenchmark().getRuns(), result.getRowsCount(), result.getQueryDuration());
        }
        else {
            LOG.error("Query failed: {} ({}/{}), execution error: {}", result.getQueryName(), result.getQueryExecution().getRun(),
                    result.getBenchmark().getRuns(), result.getFailureCause().getMessage());
        }
    }
}
