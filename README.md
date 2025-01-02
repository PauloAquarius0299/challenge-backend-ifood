# Desafio backend IFood (AnotaAi)
Resolvendo desafio vaga backend com Java Spring, MongoDB, AWS, Lambda, S3, SQS e SQS

## Introdução 
* Criei as entidades Categoria e Produto.
* Configurei os serviços da AWS e do MongoDB.
* Criei os repositórios necessários para o gerenciamento de dados.
* Desenvolvi os serviços responsáveis pela construção da API, garantindo que os dados sejam retornados no formato JSON.
* No controlador, mapeei corretamente as requisições para que os endpoints funcionem adequadamente.

## Codigo para a lambda 
A função em Nodejs para AWS Lambda projetada para processar mensagens a fila SQS e atualizar objetos armazenados no bucket S3.
```
import { S3Client, GetObjectCommand, PutObjectCommand } from "@aws-sdk/client-s3"; 

const client = new S3Client({ region: "us-east-1" }); 

export const handler = async(event) => {
    try{
        for(const record of event.Records){
            console.log("Iniciando processamento de mensagem", record)

            const rawBody = JSON.parse(record.body);
            const body = JSON.parse(rawBody.Message);
            const ownerId = body.ownerId;

            try{
                var bucketName = "anotation-ifood-catalog-marketplace"
                var fileName = `${ownerId}-catalog.json`;
                const catalog = await getS3Object(bucketName, fileName);
                const catalogData = JSON.parse(catalog);

                if(body.type == "product"){
                    updateOrAddItem(catalogData.products, body)
                }else{
                    updateOrAddItem(catalogData.categories, body)
                }

                await putS3Object(bucketName, fileName, JSON.stringify(catalogData));

            } catch(e){
                if(error.message == "error getting object  from bucket") {
                    const newCatalog = {products: [], categories: []}
                    if(body.type == "product"){
                        newCatalog.products.push(body);
                    } else {
                        newCatalog.categories.push(body);
                    }
                    await putS3Object(bucketName, fileName, JSON.stringify(newCatalog)); }
                    else {
                        throw error;
                    }
            }
        }
        return {status: "success"}
    } catch(error) {
        console.log("Error", error)
        throw new Error("Erro ao processar mensagem SQS");
    }
}

async function getS3Object(bucket, key){
    const getCommand = new GetObjectCommand({
      Bucket: bucket,
      Key: key
    });
  
    try {
      const response = await client.send(getCommand);
      return streamToString(response.Body);
    } catch (error) {
      throw new Error("Erro ao obter o object do s3")
    }
};

function updateOrAddItem(catalog, items){
    const index = catalog.findIndex(item => item.id === items.id);
    if (index !== -1) {
        catalog[index] = {...catalog[index], ...items}
    } else {
        catalog.push(items);
    }
}

async function putS3Object(dstBucket, dstKey, content){
    try {
        const putCommand = new PutObjectCommand({
            Bucket: dstBucket,
            Key: dstKey,
            Body: content,
            ContentType: "application/json"
        });
        const putResult = await client.send(putCommand);
        return putResult;
    } catch (error) {
        console.log(error);
        return;
    }
}

function streamToString(stream) {
    return new Promise((resolve, reject) => {
      const chunks = []; 
      stream.on("data", (chunk) => chunks.push(chunk));
      stream.on("end", () => resolve(Buffer.concat(chunks).toString("utf-8"))); 
      stream.on("error", reject); 
    });
};
```
## Link do desafio: 
https://github.com/githubanotaai/new-test-backend-nodejs
