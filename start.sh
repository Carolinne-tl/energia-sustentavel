#!/bin/bash

echo "================================="
echo "Energia Sustentável - API REST"
echo "================================="
echo ""
echo "Iniciando o projeto..."
echo ""

# Verifica se Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não está instalado. Por favor, instale o Docker primeiro."
    exit 1
fi

# Verifica se Docker Compose está instalado
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose não está instalado. Por favor, instale o Docker Compose primeiro."
    exit 1
fi

echo "✅ Docker e Docker Compose encontrados!"
echo ""

# Para containers existentes
echo "🛑 Parando containers existentes (se houver)..."
docker-compose down

# Inicia os containers
echo "🚀 Iniciando containers..."
docker-compose up -d

echo ""
echo "⏳ Aguardando inicialização dos serviços..."
echo "   (Isso pode levar alguns minutos na primeira execução)"
echo ""

# Aguarda o banco de dados ficar pronto
echo "📊 Aguardando Oracle Database..."
sleep 30

# Aguarda a aplicação ficar pronta
echo "🌱 Aguardando Spring Boot Application..."
sleep 30

echo ""
echo "================================="
echo "✅ Aplicação iniciada com sucesso!"
echo "================================="
echo ""
echo "📡 Acesse a aplicação em:"
echo "   http://localhost:8080"
echo ""
echo "📚 Documentação Swagger:"
echo "   http://localhost:8080/swagger-ui.html"
echo ""
echo "🔐 Credenciais padrão:"
echo "   Username: admin"
echo "   Password: admin123"
echo ""
echo "📋 Para ver os logs:"
echo "   docker-compose logs -f app"
echo ""
echo "🛑 Para parar a aplicação:"
echo "   docker-compose down"
echo ""
