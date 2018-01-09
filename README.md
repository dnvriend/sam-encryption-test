# sam-encryption-test
A small study project on AWS KMS Encryption using [AWS KMS](https://aws.amazon.com/kms/), 
[AWS Lambda](https://aws.amazon.com/lambda/) and [sbt-sam](https://github.com/dnvriend/sbt-sam).

## Introduction
The example shows how to create 'structured data' and encrypt it with the `SamSerializer`. The record format is 
a `SamRecord`, an envelope that groups data with metadata like a schema tag/hit in the form of a schema fingerprint,
the KMS CMK and the payload itself.

## Key Policy
Key policies are the primary way to control access to customer master keys (CMKs) in AWS KMS. A key policy is a document 
to specify permissions. see: https://docs.aws.amazon.com/kms/latest/developerguide/key-policies.html

The following key policy allows access to the cmk from all principals:

```json
{
  "Version": "2012-10-17",
  "Id": "key-default-2",
  "Statement": [
    {
      "Sid": "Enable IAM User Permissions",
      "Effect": "Allow",
      "Principal": {
        "AWS": "*"
      },
      "Action": "kms:*",
      "Resource": "*"
    }
  ]
}   
```

## Internet Access
When you enable VPC, your Lambda function will lose default internet access. If you require external internet access for your 
function, ensure that your security group allows outbound connections and that your VPC has a NAT gateway.

## Resources
- https://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/vpc-nat-gateway.html
- https://docs.aws.amazon.com/lambda/latest/dg/vpc.html
- https://aws.amazon.com/premiumsupport/knowledge-center/internet-access-lambda-function/ 