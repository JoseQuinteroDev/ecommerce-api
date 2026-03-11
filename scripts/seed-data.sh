#!/usr/bin/env bash
set -e
curl -s -X POST http://localhost:8081/auth/register -H 'Content-Type: application/json' -d '{"email":"admin@commercehub.com","password":"ChangeMe123!"}' || true
echo "Seed básico aplicado"
